package no.netb.syncrodex.models;

import no.netb.libjsqlite.annotations.Db;
import no.netb.libjsqlite.annotations.Fk;
import no.netb.libjsqlite.BaseModel;

import java.sql.Timestamp;

public class FsNode extends BaseModel {

    @Db
    private boolean isFile;

    @Db
    private String name;

    @Db
    private String hash;

    @Db
    private Timestamp creationDate;

    @Db
    private Timestamp modifiedDate;

    @Db(nullable = true)
    @Fk(model = FsNode.class)
    private long parentId;

    @Db
    @Fk(model = IndexRun.class)
    private long indexRunId;
}
