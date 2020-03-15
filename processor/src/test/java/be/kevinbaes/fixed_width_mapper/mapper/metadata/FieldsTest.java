package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class FieldsTest {

    private Fields twoStringFields;
    private Field<String> field1;
    private Field<String> field2;

    @BeforeEach
    void setUp() {
        field1 = new StringField("field1", 5);
        field2 = new StringField("field2", 10);

        twoStringFields = Fields.builder()
                .addField(field1)
                .addField(field2)
                .build();
    }

    @Test
    public void objectMetadataThrowsOnDuplicateName() {
        StringField duplicate = new StringField("field1", 10);

        Fields.FieldsBuilder builder = Fields.builder()
                .addField(field1);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> builder.addField(duplicate));
    }

    @Test
    public void parse_returnsTheFieldParsedValue() {
        StringField txt = new StringField("txt", 5);
        IntegerField num = new IntegerField("num", 6);

        Fields fields = Fields.builder().addField(txt). addField(num).build();

        String encoded = format("%5s%6s", "foo", 56);

        assertThat(fields.parse(encoded, txt).trim()).isEqualTo("foo");
        assertThat(fields.parse(encoded, num)).isEqualTo(56);
    }

    @Test
    public void split_returnsMapWithAllParsedValues() {
        StringField txt = new StringField("txt", 5);
        IntegerField num = new IntegerField("num", 6);
        IntegerField repeatVal = new IntegerField("val", 1);
        RepeatingFieldWithCounter<Integer> repeat = new RepeatingFieldWithCounter<>("repeating", new IntegerField("counter", 1), repeatVal);

        Fields fields = Fields.builder().addField(txt). addField(num).addField(repeat).build();

        String encoded = format("%5s%6s234", "foo", 56);

        Map<String, String> split = fields.split(encoded);
        assertThat(split.get("txt").trim()).isEqualTo("foo");
        assertThat(split.get("num").trim()).isEqualTo("56");
        assertThat(split.get("repeating")).isEqualTo("234");
    }

    @Test
    public void getFieldAsText() {
        field1 = new StringField("field1", 5);
        field2 = new StringField("field2", 10);

        twoStringFields = Fields.builder()
                .addField(field1)
                .addField(field2)
                .build();

        String encoded = format("%5s, %10s", "text1", "text2");

        assertThat(twoStringFields.getFieldAsText(encoded, "field1")).isEqualTo("text1");
    }

    @Test
    public void embeddedRepeatedFieldTest() {
        Field<Integer> counter = new IntegerField("counter", 2);
        Field<List<String>> repeatingField = new RepeatingFieldWithCounter<>("repeating", counter, new StringField("template", 2));
        Field<String> afterrepeat = new StringField("afterrepeat", 2);

        Fields fields = Fields.builder()
                .addField(repeatingField)
                .addField(afterrepeat)
                .build();

        String encoded = " 2 a b c";
        assertThat(fields.getFieldAsText(encoded, repeatingField.getName())).isEqualTo(" 2 a b");
        assertThat(fields.getFieldAsText(encoded, afterrepeat.getName())).isEqualTo(" c");
    }

}