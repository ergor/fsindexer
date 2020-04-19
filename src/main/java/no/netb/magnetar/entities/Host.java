package no.netb.magnetar.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(columnDefinition = "TEXT")
    private String displayName;

    @Column(columnDefinition = "TEXT")
    private String fqdn;

    @OneToMany(mappedBy = "host")
    private List<HostAddress> addresses;

    public Host() {
    }

    public long getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFqdn() {
        return fqdn;
    }

    public void setFqdn(String fqdn) {
        this.fqdn = fqdn;
    }

    public List<HostAddress> getAddresses() {
        return addresses;
    }
}
