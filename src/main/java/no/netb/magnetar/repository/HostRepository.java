package no.netb.magnetar.repository;

import no.netb.libjsqlite.Database;
import no.netb.magnetar.models.Host;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class HostRepository extends AbstractRepository {

    private static final Logger LOG = Logger.getLogger(HostRepository.class.getName());

    public HostRepository(Database database) {
        super(database);
    }

    public List<Host> getHosts() throws IllegalAccessException, SQLException, InstantiationException {
        return database.selectN(Host.class, "");
    }
}
