package no.netb.models;

import no.netb.annotations.Db;
import no.netb.annotations.Fk;

import java.sql.Timestamp;

public class IndexRun extends ModelBase {

    @Db
    private Timestamp timestamp;

    @Db
    @Fk(Host.class)
    private long hostId;

    public IndexRun(Host host) {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.hostId = host.getId();
    }
}
