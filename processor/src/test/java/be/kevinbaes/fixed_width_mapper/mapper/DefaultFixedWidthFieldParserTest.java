package be.kevinbaes.fixed_width_mapper.mapper;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class DefaultFixedWidthFieldParserTest {

    private FixedWidthFieldParser defaultFixedWidthFieldParser = DefaultFixedWidthFieldParser.withDefaultMappers();

    @Test
    public void parseStringField() {
        String partOfEncodedString = "part";
        assertThat(defaultFixedWidthFieldParser.parseValue(partOfEncodedString, String.class)).isEqualTo("part");
    }

    @Test
    public void parseIntField() {
        String partOfEncodedString = "1";
        int parse = defaultFixedWidthFieldParser.parseValue(partOfEncodedString, Integer.class);
        assertThat(parse).isEqualTo(1);
    }

    @Test
    public void unknownTypeThrows() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> defaultFixedWidthFieldParser.parseValue("bla", Calendar.class))
                .withMessage("type [java.util.Calendar] not supported");
    }

    @Test
    public void registerMapperThrowsIfMapperForTypeAlreadyExists() {
        FixedWidthFieldParser parser = new DefaultFixedWidthFieldParser();

        parser.registerMapper(String.class, s -> s);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> parser.registerMapper(String.class, s -> s));
    }

    @Test
    public void parseField() {
        String partOfEncodedString = "1";
        FixedWidthFieldMetadata<Integer> integerField = new FixedWidthIntegerFieldMetadata("foo", 1);

        int result = defaultFixedWidthFieldParser.parseSingleField(partOfEncodedString, integerField);

        assertThat(result).isEqualTo(1);
    }


    @Test
    public void parseFieldFromObject() {
        FixedWidthFieldMetadata<Integer> integerField = new FixedWidthIntegerFieldMetadata("foo", 4);
        FixedWidthFieldMetadata<String> stringField = new FixedWidthStringFieldMetadata("bar", 20);

        FlatFixedWidthObjectMetadata metadata = FlatFixedWidthObjectMetadata.builder()
                .add(integerField)
                .add(stringField)
                .build();

        String partOfEncodedString = String.format("%4s%20s", 23, "description");
        int result = defaultFixedWidthFieldParser.parseFieldFromObject(partOfEncodedString, integerField, metadata);
        String stringResult = defaultFixedWidthFieldParser.parseFieldFromObject(partOfEncodedString, stringField, metadata).trim();

        assertThat(result).isEqualTo(23);
        assertThat(stringResult).isEqualTo("description");
    }
}
