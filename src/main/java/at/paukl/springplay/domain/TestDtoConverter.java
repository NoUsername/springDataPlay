package at.paukl.springplay.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Paul Klingelhuber
 */
@Converter(autoApply = true)
public class TestDtoConverter implements AttributeConverter<TestDto, String> {

    @Override
    public String convertToDatabaseColumn(TestDto attribute) {
        return attribute == null ? null : attribute.value.toLowerCase();
    }

    @Override
    public TestDto convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new TestDto(dbData);
    }
}
