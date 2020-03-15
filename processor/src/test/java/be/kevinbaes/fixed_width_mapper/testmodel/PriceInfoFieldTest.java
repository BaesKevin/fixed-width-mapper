package be.kevinbaes.fixed_width_mapper.testmodel;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PriceInfoFieldTest {

    @Test
    void toFullWidthString() {
        PriceInfoField priceinfofield = new PriceInfoField("priceinfo");

        assertThat(priceinfofield.toFullWidthString(new PriceInfo(3, 4)))
                .isEqualTo("    3    4");
    }
}

