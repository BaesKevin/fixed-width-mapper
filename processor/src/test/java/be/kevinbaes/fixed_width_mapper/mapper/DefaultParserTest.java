package be.kevinbaes.fixed_width_mapper.mapper;

import be.kevinbaes.fixed_width_mapper.mapper.metadata.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class DefaultParserTest {

    @Test
    public void parseSingleField() {
        String partOfEncodedString = "1";
        Field<Integer> integerField = new IntegerField("foo", 1);

        Parser parser = DefaultParser.builder()
                .withEncodedString(partOfEncodedString)
                .withFields(integerField)
                .build();

        int result = parser.getValueFor(integerField);

        assertThat(result).isEqualTo(1);
    }

    @Test
    public void parseFieldFromObject() {
        Field<Integer> integerField = new IntegerField("foo", 4);
        Field<String> stringField = new StringField("bar", 20);

        String partOfEncodedString = String.format("%4s%20s", 23, "description");

        Parser parser = DefaultParser.builder()
                .withEncodedString(partOfEncodedString)
                .withFields(integerField, stringField)
                .build();
        int result = parser.getValueFor(integerField);
        String stringResult = parser.getValueFor(stringField).trim();

        assertThat(result).isEqualTo(23);
        assertThat(stringResult).isEqualTo("description");
    }

    @Test
    public void builderWithDefaultMappersAndMetadata() {
        String partOfEncodedString = String.format("%20s", "description");
        Field<String> stringField = new StringField("bar", 20);

        Parser parser = DefaultParser.builder()
                .withEncodedString(partOfEncodedString)
                .withFields(stringField)
                .build();

        assertThat(parser.getValueFor(stringField).trim()).isEqualTo("description");
    }

    @Test
    public void toBuilderReturnsBuilderWithPreviousData() {
        String partOfEncodedString = String.format("%20s", "description");
        String otherString = String.format("%20s", "other");
        Field<String> stringField = new StringField("bar", 20);

        DefaultParser original = DefaultParser.builder()
                .withEncodedString(partOfEncodedString)
                .withFields(stringField)
                .build();

        DefaultParser.DefaultParserBuilder builder = original.toBuilder();

        DefaultParser rebuild = builder.withEncodedString(otherString).build();

        assertThat(rebuild.getValueFor(stringField).trim()).isEqualTo("other");
    }

    @Test
    public void getWidthReturnsTotalNumberOfCharsReadFromFields() {
        Field<String> stringField = new StringField("part1", 2);
        Field<String> stringField2 = new StringField("part2", 3);

        String part1 = String.format("%2s", "a");
        String part2 = String.format("%3s", "b");

        Parser parser = DefaultParser.builder()
                .withEncodedString(part1 + part2)
                .withFields(stringField, stringField2)
                .build();

        assertThat(parser.getParseableCharacters()).isEqualTo(5);
    }

    @Test
    public void toStringReturnsAStringUsefullInDebugging() {
        Field<String> stringField = new StringField("part1", 2);
        Field<String> stringField2 = new StringField("part2", 3);

        String part1 = String.format("%2s", "a");
        String part2 = String.format("%3s", "b");
        String encodedString = part1 + part2;

        Parser parser = DefaultParser.builder()
                .withEncodedString(encodedString)
                .withFields(stringField, stringField2)
                .build();

        assertThat(parser.toString()).isEqualTo(
                "encoded string:\n[" + encodedString + "]\n" +
                        "part1 = [ a] (2 chars)\n" +
                        "part2 = [  b] (3 chars)"
        );
    }

    @Test
    public void parsingEncodedStringThatIsTooShortThrowsWithDebugString() {
        Field<String> stringField = new StringField("part1", 3);

        DefaultParser.DefaultParserBuilder builder = DefaultParser.builder()
                .withFields(stringField);

        assertThat(builder.withEncodedString("1234").build().getParseableCharacters()).isEqualTo(3);
        assertThatExceptionOfType(ParsingException.class).isThrownBy(() -> builder.withEncodedString("12").build());
    }

}
