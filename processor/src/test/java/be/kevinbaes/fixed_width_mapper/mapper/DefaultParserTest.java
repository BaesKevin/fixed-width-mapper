package be.kevinbaes.fixed_width_mapper.mapper;

import be.kevinbaes.fixed_width_mapper.mapper.metadata.FieldMetadata;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.ObjectMetadata;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.IntegerFieldMetadata;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.StringFieldMetadata;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class DefaultParserTest {

    private Parser defaultParser = DefaultParser.builder().withDefaultMappers().build();

    @Test
    public void parseStringField() {
        String partOfEncodedString = "part";
        assertThat(defaultParser.parseValue(partOfEncodedString, String.class)).isEqualTo("part");
    }

    @Test
    public void parseIntField() {
        String partOfEncodedString = "1";
        int parse = defaultParser.parseValue(partOfEncodedString, Integer.class);
        assertThat(parse).isEqualTo(1);
    }

    @Test
    public void unknownTypeThrows() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> defaultParser.parseValue("bla", Calendar.class))
                .withMessage("type [java.util.Calendar] not supported");
    }

    @Test
    public void registerMapperThrowsIfMapperForTypeAlreadyExists() {
        Parser parser = DefaultParser.builder().build();

        parser.registerMapper(String.class, s -> s);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> parser.registerMapper(String.class, s -> s));
    }

    @Test
    public void parseField() {
        String partOfEncodedString = "1";
        FieldMetadata<Integer> integerField = new IntegerFieldMetadata("foo", 1);

        Parser parser = DefaultParser.builder().withDefaultMappers().withEncodedString(partOfEncodedString).build();

        int result = parser.parseSingleField(integerField);

        assertThat(result).isEqualTo(1);
    }

    @Test
    public void parseFieldFromObject() {
        FieldMetadata<Integer> integerField = new IntegerFieldMetadata("foo", 4);
        FieldMetadata<String> stringField = new StringFieldMetadata("bar", 20);

        ObjectMetadata metadata = ObjectMetadata.builder()
                .addField(integerField)
                .addField(stringField)
                .build();

        String partOfEncodedString = String.format("%4s%20s", 23, "description");

        Parser parser = DefaultParser.builder()
                .withDefaultMappers()
                .withEncodedString(partOfEncodedString)
                .withObjectMetadata(metadata)
                .build();
        int result = parser.parseFieldFromObject(integerField);
        String stringResult = parser.parseFieldFromObject(stringField).trim();

        assertThat(result).isEqualTo(23);
        assertThat(stringResult).isEqualTo("description");
    }

    @Test
    public void builderWithDefaultMappersAndMetadata() {
        String partOfEncodedString = String.format("%20s", "description");
        FieldMetadata<String> stringField = new StringFieldMetadata("bar", 20);

        Parser parser = DefaultParser.builder()
                .withDefaultMappers()
                .withEncodedString(partOfEncodedString)
                .build();

        assertThat(parser.parseSingleField(stringField));
    }

    @Test
    public void toBuilderReturnsBuilderWithPreviousData() {
        String partOfEncodedString = String.format("%20s", "description");
        String otherString = String.format("%20s", "other");
        FieldMetadata<String> stringField = new StringFieldMetadata("bar", 20);

        ObjectMetadata objectMetadata = ObjectMetadata.builder().addField(stringField).build();
        DefaultParser original = DefaultParser.builder()
                .withDefaultMappers()
                .withEncodedString(partOfEncodedString)
                .withObjectMetadata(objectMetadata)
                .build();

        DefaultParser.DefaultParserBuilder builder = original.toBuilder();

        DefaultParser rebuild = builder.withEncodedString(otherString).build();

        assertThat(rebuild.parseFieldFromObject(stringField).trim()).isEqualTo("other");
    }

}
