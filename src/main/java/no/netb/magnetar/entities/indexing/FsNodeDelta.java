package no.netb.magnetar.entities.indexing;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FsNodeDelta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(columnDefinition = "TEXT")
    private String path;

    /**
     * To track local changes (i.e. for a single host, not _across_ hosts)
     */
    @Column(columnDefinition = "TEXT")
    private String diff;

    private DeltaType deltaType;

    /**
     * If this delta is a deletion, this field points to the deleted node.
     * Otherwise, it points to a new node which may be completely new
     * or overriding previous version.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fsNodeId")
    private FsNode fsNode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indexingRunId")
    private IndexingRun indexingRun;


    public enum DeltaType {
        DELETION(0),
        CREATION(1),
        MODIFICATION(2);

        private final int value;
        private static final Map<Integer, DeltaType> valueMap;

        DeltaType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static DeltaType of(Integer value) {
            return valueMap.get(value);
        }

        static {
            Map<Integer, DeltaType> map = new HashMap<>();
            for (DeltaType dt : DeltaType.values()) {
                map.put(dt.value, dt);
            }
            valueMap = Collections.unmodifiableMap(map);
        }
    }
}
