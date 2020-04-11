package no.netb.magnetar.models;

import no.netb.libjsqlite.EnumColumn;
import no.netb.libjsqlite.annotations.Db;
import no.netb.libjsqlite.annotations.Fk;
import no.netb.libjsqlite.BaseModel;

import java.sql.Timestamp;

public class FsNode extends BaseModel {

    public enum NodeType implements EnumColumn {
        FILE(0),
        DIRECTORY(1),
        SYMLINK(2),
        OTHER(3);

        private int value;

        NodeType(int value) {
            this.value = value;
        }

        @Override
        public int toInt() {
            return value;
        }
    }

    @Db
    private NodeType nodeType;

    @Db
    private String name;

    @Db
    private long size;

    @Db
    private int uid;

    @Db
    private int gid;

    @Db
    private int permissions;

    @Db
    private Timestamp creationDate;

    @Db
    private Timestamp modifiedDate;

    @Db
    private String path;

    @Db(nullable = true)
    private String linksTo;

    @Db
    private String sha1Checksum;

    @Db(nullable = true)
    @Fk(model = FsNode.class)
    private long parentId;

    @Db (nullable = true)
    @Fk(model = Host.class)
    private long hostId;

    /**
     * Idea: store new FsNodes from scratch for every IndexRun, so you can see how
     * the complete fs has changed for every run.
     */
    @Db
    @Fk(model = IndexingRun.class)
    private long indexingRunId;


    public FsNode(NodeType nodeType, String name, String path, String sha1Checksum, Timestamp creationDate, Timestamp modifiedDate, long parentId, long hostId, long indexingRunId) {
        this.nodeType = nodeType;
        this.name = name;
        this.path = path;
        this.sha1Checksum = sha1Checksum;
        this.creationDate = creationDate;
        this.modifiedDate = modifiedDate;
        this.parentId = parentId;
        this.hostId = hostId;
        this.indexingRunId = indexingRunId;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSha1Checksum() {
        return sha1Checksum;
    }

    public void setSha1Checksum(String sha1Checksum) {
        this.sha1Checksum = sha1Checksum;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getHostId() {
        return hostId;
    }

    public void setHostId(long hostId) {
        this.hostId = hostId;
    }

    public long getIndexingRunId() {
        return indexingRunId;
    }

    public void setIndexingRunId(long indexingRunId) {
        this.indexingRunId = indexingRunId;
    }
}
