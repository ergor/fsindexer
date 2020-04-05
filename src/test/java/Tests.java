
import no.netb.Indexer;
import no.netb.libjcommon.result.Result;
import no.netb.libjsqlite.Jsqlite;
import no.netb.models.Host;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class Tests {

    @Test
    public void testIndex() {
        //Result<List<Host>, Exception> selectResult = Jsqlite.selectN(Host.class, "WHERE h.id = ?", 1);
        //Result<Object, String> indexResult = Indexer.index(selectResult.unwrap().get(0), new File("/home"));


    }
}
