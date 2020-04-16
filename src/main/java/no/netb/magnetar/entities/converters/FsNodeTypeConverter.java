package no.netb.magnetar.entities.converters;

import no.netb.magnetar.entities.FsNode;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class FsNodeTypeConverter implements AttributeConverter<FsNode.NodeType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(FsNode.NodeType nodeType) {
        return nodeType.getValue();
    }

    @Override
    public FsNode.NodeType convertToEntityAttribute(Integer integer) {
        return FsNode.NodeType.of(integer);
    }
}
