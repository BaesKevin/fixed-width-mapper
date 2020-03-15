package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class RepeatingFieldTest {

    @Test
    public void parseTest() {
        Field<List<Integer>> field = new RepeatingField<>("field", 3, new IntegerField("template", 1));

        ParseResult<List<Integer>> listParseResult = field.parseWithResult("1234");

        assertThat(listParseResult.getCharsRead()).isEqualTo(3);
        assertThat(listParseResult.getValue()).containsOnly(1, 2, 3);
    }


    @Test
    public void toFullWidthString() {
        Field<List<Integer>> field = new RepeatingField<>("field", 3, new IntegerField("template", 1));

        List<Integer> numbers = asList(1, 2, 3);

        assertThat(field.toFullWidthString(numbers)).isEqualTo("123");
    }


}