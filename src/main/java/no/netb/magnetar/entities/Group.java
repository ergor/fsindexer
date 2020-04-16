package no.netb.magnetar.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int gid;
    private String name;
    private Host host;

    public Group() {
    }
}
