package no.netb.magnetar.entities.converters;

import no.netb.magnetar.entities.FsNodeDelta;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class FsNodeDeltaTypeConverter implements AttributeConverter<FsNodeDelta.DeltaType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(FsNodeDelta.DeltaType deltaType) {
        return deltaType.getValue();
    }

    @Override
    public FsNodeDelta.DeltaType convertToEntityAttribute(Integer dbData) {
        return FsNodeDelta.DeltaType.of(dbData);
    }
}
