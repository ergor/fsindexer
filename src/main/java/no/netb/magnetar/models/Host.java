package no.netb.magnetar.models;

import no.netb.libjsqlite.annotations.Db;
import no.netb.libjsqlite.BaseModel;

public class Host extends BaseModel {

    @Db
    private String name;

    @Db(nullable = true)
    private String sshConfigName;

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

    public String getSshConfigName() {
        return sshConfigName;
    }

    public void setSshConfigName(String sshConfigName) {
        this.sshConfigName = sshConfigName;
    }
}
