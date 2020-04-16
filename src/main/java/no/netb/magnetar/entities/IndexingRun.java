package no.netb.magnetar.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class IndexingRun {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Timestamp timestamp;
    public IndexingRun() {
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }
}
