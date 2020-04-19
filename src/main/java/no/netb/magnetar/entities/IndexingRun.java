package no.netb.magnetar.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class IndexingRun {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Timestamp timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostId")
    private Host host;

    /**
     * When doing an update run (e.g. a run triggered by an inotify event), and we
     * are only updating a subset of the index, we link it to the original run with
     * this id.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentRunId")
    private IndexingRun parentRun;

    public IndexingRun() {
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public IndexingRun(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
