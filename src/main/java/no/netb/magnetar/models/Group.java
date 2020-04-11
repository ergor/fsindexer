package no.netb.magnetar.models;

import no.netb.libjsqlite.annotations.Db;
import no.netb.libjsqlite.annotations.Fk;

public class Group {

    @Db
    private int gid;

    @Db
    private String name;

    @Db
    @Fk(model = Host.class)
    private long hostId;
}
