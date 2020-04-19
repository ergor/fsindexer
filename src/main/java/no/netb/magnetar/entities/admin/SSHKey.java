package no.netb.magnetar.entities.admin;

import javax.persistence.*;

@Entity
public class SSHKey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
}
