package no.netb.magnetar.repository;

import no.netb.libjcommon.result.Result;
import no.netb.libjsqlite.Database;
import no.netb.magnetar.models.Host;

import java.util.List;
import java.util.logging.Logger;

public class HostRepository extends AbstractRepository {

    private static final Logger LOG = Logger.getLogger(HostRepository.class.getName());

    public HostRepository(Database database) {
        super(database);
    }

    public Result<List<Host>, Exception> getHosts() {
        return database.selectN(Host.class, "");
    }
}
