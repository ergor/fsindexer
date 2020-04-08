package no.netb.magnetar.repository;

import no.netb.libjcommon.result.Result;
import no.netb.libjsqlite.Database;
import no.netb.magnetar.models.FsNode;
import no.netb.magnetar.models.Host;
import no.netb.magnetar.models.IndexingRun;

import java.io.File;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

public class FsNodeRepository {

    private static final Logger LOG = Logger.getLogger(FsNodeRepository.class.getName());

    private Database database;

    private Map<IndexingRun, Map<File, FsNode>> fsNodeCache = new HashMap<>();

    public FsNodeRepository(Database database) {
        this.database = database;
    }

    private Optional<FsNode> getCached(IndexingRun indexingRun, File file) {
        Map<File, FsNode> cache = fsNodeCache.computeIfAbsent(indexingRun, k -> new HashMap<>());
        return Optional.ofNullable(cache.get(file));
    }

    private void putCache(IndexingRun indexingRun, File file, FsNode fsNode) {
        fsNodeCache.get(indexingRun).put(file, fsNode);
    }

    public Optional<FsNode> get(IndexingRun indexingRun, File file) {
        Optional<FsNode> cachedNode = getCached(indexingRun, file);
        if (cachedNode.isPresent()) {
            return cachedNode;
        }

        Result<List<FsNode>, Exception> selectResult = database.selectN(
                FsNode.class,
                "WHERE f.indexingRunId = ? AND f.isFile = ? AND f.name = ? AND f.path = ?",
                indexingRun.getId(),
                file.isFile(),
                file.getName(),
                file.getAbsolutePath());

        if (selectResult.isErr()) {
            throw new RuntimeException("FIXME", selectResult.unwrapErr());
        }
        List<FsNode> fsNodes = selectResult.unwrap();
        if (fsNodes.isEmpty()) {
            return Optional.empty();
        } else if (fsNodes.size() > 1) {
            LOG.warning(String.format("WARNING: select returned %d FsNodes (expected 1)", fsNodes.size()));
        }
        FsNode fsNode = fsNodes.get(0);
        putCache(indexingRun, file, fsNode);
        return Optional.of(fsNode);
    }

    public FsNode getOrNew(Host host, IndexingRun indexingRun, File file, File parentDir) {
        Optional<FsNode> fsNodeOptional = get(indexingRun, file);
        if (fsNodeOptional.isPresent()) {
            return fsNodeOptional.get();
        }

        FsNode parentNode = null;
        if (parentDir != null) { // null means this dir is root
            Optional<FsNode> parentOptional = get(indexingRun, parentDir);
            parentNode = parentOptional.orElseGet(
                    () -> getOrNew(host, indexingRun, parentDir, parentDir.getParentFile()));
        }

        FsNode fsNode = new FsNode(
                file.isFile(),
                file.getName(),
                file.getPath(),
                "", // hash
                new Timestamp(0),
                new Timestamp(file.lastModified()),
                parentNode == null ? 0 : parentNode.getId(),
                host.getId(),
                indexingRun.getId()
        );

        fsNode.saveOrFail(database);
        putCache(indexingRun, file, fsNode);
        return fsNode;
    }
}
