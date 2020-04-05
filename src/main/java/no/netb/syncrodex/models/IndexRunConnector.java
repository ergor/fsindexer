package no.netb.syncrodex.models;

import no.netb.libjsqlite.annotations.Db;
import no.netb.libjsqlite.annotations.Fk;
import no.netb.libjsqlite.BaseModel;

/**
 * To manually connect multiple index runs and mark them as logically 1 index run.
 *
 * Use case: you index localhost, remote A and remote B in separate runs,
 * and between the runs you are sure you didn't modify the dirs under indexing.
 * Then you can "connect" the runs together as 1 logical run.
 */
public class IndexRunConnector extends BaseModel {

    @Db
    private String uuid; // this will be the logical run id.

    @Db
    @Fk(model = IndexRun.class)
    private long indexRunId;
}
