package project.project.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import javax.print.attribute.Attribute;
import java.util.Arrays;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>,String> {

    private static final String SPLIT_STRING="&&&";

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if(attribute==null||attribute.isEmpty()){
            return null;
        }
        return String.join(SPLIT_STRING,attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return Arrays.asList(dbData.split(SPLIT_STRING));
    }
}
