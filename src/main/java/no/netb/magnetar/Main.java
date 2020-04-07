package no.netb.magnetar;

import no.netb.libjcommon.result.Result;
import no.netb.libjsqlite.Jsqlite;
import no.netb.libjsqlite.TablesInit;
import no.netb.libjsqlite.resulttypes.updateresult.UpdateResult;
import no.netb.magnetar.models.*;

import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        // one shot mode & server mode
        // one shot mode: create db in tmp. allow nulls for host and runid etc.
        // server mode: allow for interaction via webserver. initiates one shot mode on remotes.

        UpdateResult createResult = TablesInit.createTablesIfNotExists(Arrays.asList(
                FsNode.class,
                Host.class,
                HostIdentifier.class,
                IndexingRun.class,
                IndexingRunConnector.class
        ));
        createResult.unwrap().commit();

        String hostname = "procyon";
        Result<List<Host>, Exception> hostResult = Jsqlite.selectN(Host.class, "WHERE h.name = ?", hostname);
        hostResult.getErr().ifPresent(e -> {throw new RuntimeException(e);});
        List<Host> hosts = hostResult.unwrap();
        if (hosts.isEmpty()) {
            Host host = new Host(hostname);
            host.saveOrFail();
            hosts.add(host);
        }
        Host host = hosts.get(0);
        File file = new File("/home/zzz/tmp");
        Indexer.index(host, file);
        Jsqlite.getConnection().commit();
    }
}
