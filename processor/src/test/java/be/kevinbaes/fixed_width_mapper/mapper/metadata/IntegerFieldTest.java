package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IntegerFieldTest {

    @Test
    public void parseReturnsResultWithValueAndCharactersRead() {
        Field<Integer> integerField = new IntegerField("name", 3);

        ParseResult<Integer> result = integerField.parseWithResult("34 lkajsdf");

        assertThat(result.getValue()).isEqualTo(34);
        assertThat(result.getCharsRead()).isEqualTo(3);
    }

}