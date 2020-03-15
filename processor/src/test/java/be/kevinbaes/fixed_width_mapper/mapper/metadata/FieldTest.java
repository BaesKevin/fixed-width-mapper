package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
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
        assertThat(integerField.parse("    5")).isEqualTo(5);
    }

    @Test
    public void toFullWidthStringTest() {
        Field<String> stringField = new StringField("field1", 5);
        Field<Integer> integerField = new IntegerField("field2", 5);

        IntegerField intcollectionval = new IntegerField("intcollectionval", 2);
        IntegerField intcollectioncounter = new IntegerField("intcollectioncounter", 2);
        Field<List<Integer>> intCollection = new RepeatingFieldWithCounter<>("intcollection", intcollectioncounter, intcollectionval);

        assertThat(stringField.toFullWidthString("abc")).isEqualTo("  abc");
        assertThat(integerField.toFullWidthString(34)).isEqualTo("   34");
        assertThat(intCollection.toFullWidthString(asList(1, 2, 3))).isEqualTo(" 3 1 2 3");
    }

}