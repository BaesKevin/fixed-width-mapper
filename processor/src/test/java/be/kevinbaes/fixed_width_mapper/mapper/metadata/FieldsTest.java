package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    public void startingPositionTest() {
        assertThat(twoStringFields.getStartingPosition(field1, "")).isEqualTo(0);
        assertThat(twoStringFields.getStartingPosition(field2, "")).isEqualTo(5);
    }

    @Test
    public void getStartingPositionWorksForNestedFields() {
        Field<String> stringField = new StringField("field1",7);
        Field<String> nestedField = new StringField("field2", 4);
        Fields nestedObjectMetadata = Fields.builder()
                .addField(nestedField)
                .build();

        Fields parent = Fields.builder()
                .addField(stringField)
                .build();

        assertThat(parent.getStartingPosition(stringField, "")).isEqualTo(0);
        assertThat(parent.getStartingPosition(nestedField, "")).isEqualTo(7);
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
        Field<List<String>> repeatingField = new RepeatingField<>("repeating", counter, new StringField("template", 2));
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