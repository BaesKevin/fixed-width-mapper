package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class FixedWidthFieldTest {

    private FixedWidthField<String> field;

    @BeforeEach
    void setUp() {
        field = new StringField("bla", 3);
    }

    @Test
    public void parseWithResult_cutsOfStringWhenTooLong() {
        assertThat(field.parseWithResult("1234").getValue()).isEqualTo("123");
    }

    @Test
    public void parseWithResult_throwsWhenTooShort() {
        assertThatExceptionOfType(ParsingException.class).isThrownBy(() -> field.parseWithResult("12"));
    }



}