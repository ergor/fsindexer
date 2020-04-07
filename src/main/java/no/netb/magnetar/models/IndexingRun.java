package no.netb.magnetar.models;

import no.netb.libjsqlite.annotations.Db;
import no.netb.libjsqlite.BaseModel;
import no.netb.libjsqlite.annotations.Fk;

import java.sql.Timestamp;

public class IndexingRun extends BaseModel {

    @Db
    private Timestamp timestamp;

    public IndexingRun() {
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
