package no.netb.syncrodex.models;

import no.netb.libjsqlite.annotations.Db;
import no.netb.libjsqlite.annotations.Fk;
import no.netb.libjsqlite.BaseModel;

public class HostIdentifier extends BaseModel {

    @Db
    @Fk(model = Host.class)
    private long hostId;

    @Db
    private String fqdn;

    @Db
    private String ipAddress;
}
