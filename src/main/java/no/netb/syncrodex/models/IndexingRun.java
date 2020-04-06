package no.netb.syncrodex.models;

import no.netb.libjsqlite.annotations.Db;
import no.netb.libjsqlite.BaseModel;
import no.netb.libjsqlite.annotations.Fk;

import java.sql.Timestamp;

public class IndexingRun extends BaseModel {

    @Db
    private Timestamp timestamp;

    @Db
    @Fk(model = Host.class)
    private long hostId;

    public IndexingRun(Host host) {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.hostId = host.getId();
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public long getHostId() {
        return hostId;
    }

    public void setHostId(long hostId) {
        this.hostId = hostId;
    }
}
