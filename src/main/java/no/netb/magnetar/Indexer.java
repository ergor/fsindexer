package no.netb.magnetar;

import no.netb.libjcommon.result.Result;
import no.netb.libjsqlite.Database;
import no.netb.magnetar.models.FsNode;
import no.netb.magnetar.models.Host;
import no.netb.magnetar.models.IndexingRun;
import no.netb.magnetar.repository.FsNodeRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.PosixFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Indexer {

    private static final Logger LOG = Logger.getLogger(Indexer.class.getName());

    private final int DIGEST_BLOCK_SZ = 128*1024;
    private final byte[] DIGEST_BUF = new byte[DIGEST_BLOCK_SZ];

    private Database database;
    private Host host;
    private MessageDigest sha1Digest;
    private FsNodeRepository fsNodeRepository;

    public static Result<Indexer, Exception> init(Database database, Host host) {
        try {
            MessageDigest sha1Digest = MessageDigest.getInstance("SHA-1");
            return Result.ok(new Indexer(database, host, sha1Digest));
        } catch (NoSuchAlgorithmException e) {
            return Result.err(e);
        }
    }

    private Indexer(Database database, Host host, MessageDigest sha1Digest) {
        this.database = database;
        this.host = host;
        this.sha1Digest = sha1Digest;
        fsNodeRepository = new FsNodeRepository(database);
    }

    public Result<Object, String> index(File directory) {

        if (!directory.isDirectory()) {
            return Result.err(String.format("Indexer: %s is not a directory (expected directory).", directory.getAbsolutePath()));
        }

        File[] files = directory.listFiles();

        if (files == null) {
            return Result.err(String.format("Indexer: could not list files in %s", directory.getAbsolutePath()));
        }

        IndexingRun indexingRun = new IndexingRun();
        indexingRun.saveOrFail(database);

        FsNode directoryNode = fsNodeRepository.getOrNew(host, indexingRun, directory, directory.getParentFile());
        readFileAttributes(directory, directoryNode);

        for (File file : files) {
            FsNode fileNode = fsNodeRepository.getOrNew(host, indexingRun, file, directory);
            readFileAttributes(file, fileNode);
            if (file.isFile()) {
                sha1Checksum(file).ifPresent(checksum -> {
                    fileNode.setSha1Checksum(sha1String(checksum));
                    fileNode.saveOrFail(database);
                });
            }
        }

        return Result.ok(null);
    }

    public Optional<byte[]> sha1Checksum(File file) {
        if (file.isDirectory()) {
            return Optional.empty();
        }

        try {
            FileInputStream fileStream = new FileInputStream(file);
            sha1Digest.reset();
            int bytesRead = fileStream.read(DIGEST_BUF);
            while (bytesRead > -1) {
                sha1Digest.update(DIGEST_BUF, 0, bytesRead);
                bytesRead = fileStream.read(DIGEST_BUF);
            }
            return Optional.of(sha1Digest.digest());
        }
        catch (FileNotFoundException e) {
            LOG.warning("WARNING: failed to open file for reading: " + file.getAbsolutePath());
            e.printStackTrace();
        }
        catch (IOException e) {
            LOG.warning("WARNING: failed while reading file: " + file.getAbsolutePath());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static String sha1String(byte[] bytes) {
        assert bytes.length == 20;

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    private void readFileAttributes(File file, FsNode fsNode) {
        try {
            PosixFileAttributes attrs = Files.readAttributes(file.toPath(), PosixFileAttributes.class, LinkOption.NOFOLLOW_LINKS);

        } catch (IOException e) {
            LOG.log(Level.WARNING, "Unable to read attributes of file " + file.getAbsolutePath(), e);
        }
    }
}
