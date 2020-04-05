package no.netb.models;

import no.netb.libjsqlite.annotations.Db;
import no.netb.libjsqlite.BaseModel;

public class Host extends BaseModel {

    @Db
    private String name;
}
