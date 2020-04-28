package no.netb.magnetar.entities.indexing;

import no.netb.magnetar.constants.Posix;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Entity
public class FsNode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private NodeType nodeType;

    @Column(length = 40)
    private String sha1Checksum;

    @Column(columnDefinition = "TEXT")
    private String parentPath;

    @Column(columnDefinition = "TEXT")
    private String name;

    private long size;

    private int uid;

    private int gid;

    private EnumSet<Posix.Permission> permissions;

    private Timestamp creationDate;

    private Timestamp modifiedDate;

    @Column(columnDefinition = "TEXT")
    private String linksTo; // for soft links (symlinks)

    private long inode;

    private long nlinks; // number of hard links to this inode

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private FsNode parent;

    @OneToMany(mappedBy = "parent")
    private List<FsNode> children;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostId")
    private Host host;

    //@OneToOne(fetch = FetchType.LAZY, mappedBy = "fsNode")
    //private FsNodeDelta fsNodeDelta;

    /**
     * Idea: store new FsNodes from scratch for every IndexRun, so you can see how
     * the complete fs has changed for every run.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indexingRunId")
    private IndexingRun indexingRun;

    public FsNode() {
    }

    public enum NodeType  {
        FILE(0),
        DIRECTORY(1),
        SYMLINK(2),
        OTHER(3);

        private final int value;
        private static final Map<Integer, NodeType> valueMap;

        NodeType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static NodeType of(Integer value) {
            return valueMap.get(value);
        }

        static {
            Map<Integer, NodeType> map = new HashMap<>();
            for (NodeType nt : NodeType.values()) {
                map.put(nt.value, nt);
            }
            valueMap = Collections.unmodifiableMap(map);
        }
    }
}
