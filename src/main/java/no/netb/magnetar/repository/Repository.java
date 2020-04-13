package no.netb.magnetar.repository;

import no.netb.libjsqlite.Database;

import java.util.HashMap;
import java.util.Map;

public class Repository {

    Database database;
    private Map<Class<? extends AbstractRepository>, AbstractRepository> repositories;

    public Repository(Database database) {
        this.database = database;
        this.repositories = new HashMap<>();
    }

    public void put(Class<? extends AbstractRepository> repoClass, AbstractRepository instance) {
        repositories.put(repoClass, instance);
    }

    public <T extends AbstractRepository> T get(Class<T> repoClass) {
        return (T) repositories.get(repoClass);
    }

    public Database getDatabase() {
        return database;
    }
}
