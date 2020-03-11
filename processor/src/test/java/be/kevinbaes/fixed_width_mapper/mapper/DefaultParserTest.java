package be.kevinbaes.fixed_width_mapper.mapper;

import be.kevinbaes.fixed_width_mapper.mapper.metadata.Field;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.Fields;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.IntegerField;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.StringField;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultParserTest {

    @Test
    public void parseSingleField() {
        String partOfEncodedString = "1";
        Field<Integer> integerField = new IntegerField("foo", 1);
        Fields fields = Fields.builder().addField(integerField).build();

        Parser parser = DefaultParser.builder().withEncodedString(partOfEncodedString).withFields(fields).build();

        int result = parser.parseField(integerField);

        assertThat(result).isEqualTo(1);
    }

    @Test
    public void parseFieldFromObject() {
        Field<Integer> integerField = new IntegerField("foo", 4);
        Field<String> stringField = new StringField("bar", 20);

        Fields metadata = Fields.builder()
                .addField(integerField)
                .addField(stringField)
                .build();

        String partOfEncodedString = String.format("%4s%20s", 23, "description");

        Parser parser = DefaultParser.builder()
                .withEncodedString(partOfEncodedString)
                .withFields(metadata)
                .build();
        int result = parser.parseField(integerField);
        String stringResult = parser.parseField(stringField).trim();

        assertThat(result).isEqualTo(23);
        assertThat(stringResult).isEqualTo("description");
    }

    @Test
    public void builderWithDefaultMappersAndMetadata() {
        String partOfEncodedString = String.format("%20s", "description");
        Field<String> stringField = new StringField("bar", 20);
        Fields fields = Fields.builder().addField(stringField).build();

        Parser parser = DefaultParser.builder()
                .withEncodedString(partOfEncodedString)
                .withFields(fields)
                .build();

        assertThat(parser.parseField(stringField).trim()).isEqualTo("description");
    }

    @Test
    public void toBuilderReturnsBuilderWithPreviousData() {
        String partOfEncodedString = String.format("%20s", "description");
        String otherString = String.format("%20s", "other");
        Field<String> stringField = new StringField("bar", 20);

        Fields fields = Fields.builder().addField(stringField).build();
        DefaultParser original = DefaultParser.builder()
                .withEncodedString(partOfEncodedString)
                .withFields(fields)
                .build();

        DefaultParser.DefaultParserBuilder builder = original.toBuilder();

        DefaultParser rebuild = builder.withEncodedString(otherString).build();

        assertThat(rebuild.parseField(stringField).trim()).isEqualTo("other");
    }

}
