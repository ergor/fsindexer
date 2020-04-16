package no.netb.magnetar.entities;

import javax.persistence.*;

@Entity
public class UnixUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int uid;

    @Column(columnDefinition = "TEXT")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostId")
    private Host host;

    public UnixUser() {
    }
}
