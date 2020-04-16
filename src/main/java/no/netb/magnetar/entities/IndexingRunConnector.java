package no.netb.magnetar.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * To manually connect multiple index runs and mark them as logically 1 index run.
 *
 * Use case: you index localhost, remote A and remote B in separate runs,
 * and between the runs you are sure you didn't modify the dirs under indexing.
 * Then you can "connect" the runs together as 1 logical run.
 */
@Entity
public class IndexingRunConnector {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String uuid; // this will be the logical run id.
    private IndexingRun indexingRun;

    public IndexingRunConnector() {
    }
}
