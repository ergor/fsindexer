package no.netb.magnetar;

import no.netb.libjsqlite.Database;
import no.netb.libjsqlite.Jsqlite;
import no.netb.libjsqlite.JsqliteException;
import no.netb.magnetar.models.*;
import no.netb.magnetar.repository.FsNodeRepository;
import no.netb.magnetar.repository.HostRepository;
import no.netb.magnetar.repository.Repository;

import java.sql.SQLException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws JsqliteException, SQLException {
        // one shot mode & server mode
        // one shot mode: create db in tmp. allow nulls for host and runid etc.
        // server mode: allow for interaction via webserver. initiates one shot mode on remotes.

        ArgParse argParse = new ArgParse(args);
        argParse.parse();

        Database database = Jsqlite.connect("magnetar.db", false);

        database.createTablesIfNotExists(Arrays.asList(
                FsNode.class,
                Host.class,
                HostIdentifier.class,
                IndexingRun.class,
                IndexingRunConnector.class
        ));
        database.commit();

        Repository repoInstances = new Repository(database);
        repoInstances.put(FsNodeRepository.class, new FsNodeRepository(database));
        repoInstances.put(HostRepository.class, new HostRepository(database));

        Runnable main = argParse.isServerMode()
                ? new ServerMain(repoInstances)
                : new SatelliteMain();
        main.run();

//
//        String hostname = "procyon";
//        Result<List<Host>, Exception> hostResult = Jsqlite.selectN(Host.class, "WHERE h.name = ?", hostname);
//        hostResult.getErr().ifPresent(e -> {throw new RuntimeException(e);});
//        List<Host> hosts = hostResult.unwrap();
//        if (hosts.isEmpty()) {
//            Host host = new Host(hostname);
//            host.saveOrFail();
//            hosts.add(host);
//        }
//        Host host = hosts.get(0);
//        File file = new File("/home/zzz/tmp");
//        Indexer.index(host, file);
//        Jsqlite.getConnection().commit();
    }
}
