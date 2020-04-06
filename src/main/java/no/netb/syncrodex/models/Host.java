package no.netb.syncrodex.models;

import no.netb.libjsqlite.annotations.Db;
import no.netb.libjsqlite.BaseModel;

public class Host extends BaseModel {

    @Db
    private String name;

    public Host() {
        // default constructor required for libjsqlite
    }

    public Host(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
