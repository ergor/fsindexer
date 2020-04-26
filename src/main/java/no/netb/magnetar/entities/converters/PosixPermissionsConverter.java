package no.netb.magnetar.entities.converters;

import no.netb.magnetar.constants.Posix;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.EnumSet;

@Converter(autoApply = true)
public class PosixPermissionsConverter implements AttributeConverter<EnumSet<Posix.Permission>, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumSet<Posix.Permission> permissions) {
        return permissions
                .stream()
                .map(Posix.Permission::getVal)
                .reduce(0, (bitfield, bitval) -> bitfield | bitval);
    }

    @Override
    public EnumSet<Posix.Permission> convertToEntityAttribute(Integer dbData) {

        final int bitfield = dbData;
        final EnumSet<Posix.Permission> permissions = EnumSet.noneOf(Posix.Permission.class);

        for (int i = 1; i <= Posix.Permission.MAX_VAL; i <<= 1) {
            Posix.Permission perm = Posix.Permission.of(i);
            if ((bitfield & i) > 0 && perm != null) {
                permissions.add(perm);
            }
        }

        return permissions;
    }
}
