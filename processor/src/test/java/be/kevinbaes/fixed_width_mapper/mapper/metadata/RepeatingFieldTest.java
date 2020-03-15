package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RepeatingFieldTest {

    @Test
    public void repeatingFieldTest() {
        Field<Integer> countingField = new IntegerField("repeatingcounter", 2);
        RepeatingField<String> repeatField = new RepeatingField<>("repeatingfield", countingField, new StringField("base", 2));

        ParseResult<List<String>> parsed = repeatField.parseWithResult(" 3 a b c");
        assertThat(parsed.getValue()).contains(" a", " b", " c");
        assertThat(parsed.getCharsRead()).isEqualTo(8);
    }

    @Test
    public void repeatingFieldWithIntegerTemplate() {
        Field<Integer> countingField = new IntegerField("repeatingcounter", 2);
        RepeatingField<Integer> repeatField = new RepeatingField<>("repeatingfield", countingField, new IntegerField("base", 2));

        List<Integer> parsed = repeatField.parse(" 3 5 6 7");
        assertThat(parsed).contains(5, 6, 7);
    }



}