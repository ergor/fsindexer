package no.netb.syncrodex.models;

import no.netb.libjsqlite.annotations.Db;
import no.netb.libjsqlite.annotations.Fk;
import no.netb.libjsqlite.BaseModel;

import java.sql.Timestamp;

public class IndexRun extends BaseModel {

    @Db
    private Timestamp timestamp;

    @Db
    @Fk(model = Host.class)
    private long hostId;

    public IndexRun(Host host) {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.hostId = host.getId();
    }
}
