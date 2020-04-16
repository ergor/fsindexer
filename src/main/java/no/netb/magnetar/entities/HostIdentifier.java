package no.netb.magnetar.entities;

import javax.persistence.*;

@Entity
public class HostIdentifier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostId")
    private Host host;

    @Column(columnDefinition = "TEXT")
    private String fqdn;

    @Column(columnDefinition = "TEXT")
    private String ipAddress;

    public HostIdentifier() {
    }
}
