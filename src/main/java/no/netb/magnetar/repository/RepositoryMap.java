package no.netb.magnetar.repository;

import java.util.HashMap;

public class RepositoryMap extends HashMap<Class<? extends AbstractRepository>, AbstractRepository> {

    public <T extends AbstractRepository> T getRepo(Class<T> repoClass) {
        return (T) this.get(repoClass);
    }
}
