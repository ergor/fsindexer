package no.netb.magnetar;

import no.netb.libjcommon.result.Result;
import no.netb.libjsqlite.Database;
import no.netb.libjsqlite.Jsqlite;
import no.netb.libjsqlite.resulttypes.updateresult.UpdateResult;
import no.netb.magnetar.models.*;

import java.sql.SQLException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        // one shot mode & server mode
        // one shot mode: create db in tmp. allow nulls for host and runid etc.
        // server mode: allow for interaction via webserver. initiates one shot mode on remotes.

        ArgParse argParse = new ArgParse(args);
        argParse.parse();

        Result<Database, SQLException> connectResult = Jsqlite.connect("magnetar.db", false);
        Database database = connectResult.unwrap();

        UpdateResult createResult = database.createTablesIfNotExists(Arrays.asList(
                FsNode.class,
                Host.class,
                HostIdentifier.class,
                IndexingRun.class,
                IndexingRunConnector.class
        ));
        createResult.unwrap().commit();

        Runnable main = argParse.isServerMode()
                ? new ServerMain()
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
