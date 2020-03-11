package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FieldTest {

    @Test
    public void isEqualTo_trueWhenNameSame() {
        StringField field1 = new StringField("field1", 5);
        StringField equalField = new StringField("field1", 2);
        assertTrue(field1.isEqualTo(equalField));
    }

    @Test
    public void parseReturnsValue() {
        Field<String> stringField = new StringField("field1", 5);
        Field<Integer> integerField = new IntegerField("field2", 5);

        assertThat(stringField.parse("text1")).isEqualTo("text1");
        assertThat(integerField.parse("5")).isEqualTo(5);
    }

}