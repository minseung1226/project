package project.project.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.persistence.EnumType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class EnumListConverter<T extends Enum<T>> implements AttributeConverter<List<T>,String> {

    private static final String SPLIT_CHAR=",";


    @Override
    public String convertToDatabaseColumn(List<T> attribute) {
        if(attribute==null ||attribute.isEmpty()){
            return null;
        }
        return String.join(SPLIT_CHAR
                ,attribute.stream().map(Enum::name).toArray(String[]::new));
    }

    @Override
    public List<T> convertToEntityAttribute(String dbData) {
        if(dbData==null || dbData.isEmpty()){
            return null;
        }
        return Arrays.stream(dbData.split(SPLIT_CHAR))
                .map(value->Enum.valueOf((Class<T>) EnumType.class,value)).collect(Collectors.toList());
    }
}
