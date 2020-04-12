package no.netb.magnetar.repository;

import no.netb.libjsqlite.Database;

public abstract class AbstractRepository {

    protected Database database;

    public AbstractRepository(Database database) {
        this.database = database;
    }
}
