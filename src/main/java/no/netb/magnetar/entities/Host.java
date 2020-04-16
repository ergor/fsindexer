package no.netb.magnetar.entities;

import javax.persistence.*;

@Entity
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(columnDefinition = "TEXT")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String sshConfigName;

    public Host() {
    }
}
