package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class LocalDateFieldTest {

    @Test
    void dateWithFormatTest() {
        LocalDateField time = new LocalDateField("time", "yyyy-MM-dd");

        LocalDate januaryFirst = LocalDate.of(2020, 1, 1);
        String januaryFirstTxt = "2020-01-01";
        assertThat(time.parseWithResult(januaryFirstTxt).getValue()).isEqualTo(januaryFirst);
        assertThat(time.toFullWidthString(januaryFirst)).isEqualTo(januaryFirstTxt);
    }
}