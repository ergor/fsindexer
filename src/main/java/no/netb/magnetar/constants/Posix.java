package no.netb.magnetar.constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Posix {

    public enum Permission {
        SOCKET(0140000),    // socket
        SYMLINK(0120000),   // symbolic link
        FILE(0100000),      // regular file
        BLKDEV(0060000),    // block device
        DIR(0040000),       // directory
        CHARDEV(0020000),   // character device
        FIFO(0010000),      // FIFO

        SET_UID(04000),     // set-user-ID bit (see execve(2))
        SET_GID(02000),     // set-group-ID bit
        STICKY(01000),      // sticky bit

        USER_R(00400),      // owner has read permission
        USER_W(00200),      // owner has write permission
        USER_X(00100),      // owner has execute permission
        GROUP_R(00040),     // group has read permission
        GROUP_W(00020),     // group has write permission
        GROUP_X(00010),     // group has execute permission
        OTHER_R(00004),     // others have read permission
        OTHER_W(00002),     // others have write permission
        OTHER_X(00001),     // others have execute permission
        ;
        private int val;
        private static final Map<Integer, Posix.Permission> VALUE_MAP;
        public static final int MAX_VAL;

        Permission(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public static Posix.Permission of(Integer value) {
            return VALUE_MAP.get(value);
        }

        static {
            Map<Integer, Posix.Permission> map = new HashMap<>();
            for (Posix.Permission perm : Posix.Permission.values()) {
                map.put(perm.val, perm);
            }
            VALUE_MAP = Collections.unmodifiableMap(map);

            MAX_VAL = Collections.max(VALUE_MAP.keySet());
        }
    }
}
