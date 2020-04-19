package no.netb.magnetar.entities.admin;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String userName;

    private String passwordHash;

    @OneToMany(mappedBy = "user")
    private List<SSHKey> sshKeys;
}
