package no.netb.magnetar.entities.indexing;


import javax.persistence.*;

@Entity
public class UnixGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int gid;

    @Column(columnDefinition = "TEXT")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostId")
    private Host host;

    public UnixGroup() {
    }
}
