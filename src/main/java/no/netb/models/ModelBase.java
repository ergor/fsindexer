package no.netb.models;

import no.netb.annotations.Db;
import no.netb.annotations.Pk;

public abstract class ModelBase {

    @Db
    @Pk
    private long id;

    public long getId() {
        return id;
    }
}
