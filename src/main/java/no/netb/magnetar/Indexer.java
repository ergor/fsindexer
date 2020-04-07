package no.netb.magnetar;

import no.netb.libjcommon.result.Result;
import no.netb.magnetar.models.FsNode;
import no.netb.magnetar.models.Host;
import no.netb.magnetar.models.IndexingRun;
import no.netb.magnetar.repository.FsNodeRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class Indexer {

    private static final int DIGEST_BLOCK_SZ = 128*1024;
    private static final byte[] DIGEST_BUF = new byte[DIGEST_BLOCK_SZ];
    private static MessageDigest sha1Digest;
    static {
        try {
            sha1Digest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            sha1Digest = null;
        }
    }

    public static Result<Object, String> index(Host host, File directory) {

        if (sha1Digest == null) {
            return Result.err("Indexer: message digest instance null");
        }

        if (!directory.isDirectory()) {
            return Result.err(String.format("Indexer: %s is not a directory (expected directory).", directory.getAbsolutePath()));
        }

        File[] files = directory.listFiles();

        if (files == null) {
            return Result.err(String.format("Indexer: could not list files in %s", directory.getAbsolutePath()));
        }

        IndexingRun indexingRun = new IndexingRun(host);
        indexingRun.saveOrFail();

        FsNode directoryNode = FsNodeRepository.getOrNew(indexingRun, directory, directory.getParentFile());

        for (File file : files) {
            FsNode fileNode = FsNodeRepository.getOrNew(indexingRun, file, directory);
            if (file.isFile()) {
                sha1Checksum(file).ifPresent(checksum -> {
                    fileNode.setSha1Checksum(sha1String(checksum));
                    fileNode.saveOrFail();
                });
            }
        }

        return Result.ok(null);
    }

    public static Optional<byte[]> sha1Checksum(File file) {
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
            System.out.println("WARNING: failed to open file for reading: " + file.getAbsolutePath());
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("WARNING: failed while reading file: " + file.getAbsolutePath());
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
}
