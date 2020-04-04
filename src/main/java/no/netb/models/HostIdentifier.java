package no.netb.models;

import no.netb.annotations.Db;
import no.netb.annotations.Fk;

public class HostIdentifier extends ModelBase {

    @Db
    @Fk(Host.class)
    private long hostId;

    @Db
    private String fqdn;

    @Db
    private String ipAddress;
}
