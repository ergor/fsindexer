package no.netb.magnetar.entities.indexing;

import javax.persistence.*;

@Entity
public class HostAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostId")
    private Host host;

    @Column(columnDefinition = "TEXT")
    private String ipAddress;

    public HostAddress() {
    }
}
