package be.kevinbaes.fixed_width_mapper.testmodel;

import org.junit.jupiter.api.Test;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    public void parseString_returnsProduct() {
        Product product = new Product().fromFixedWidthString(format("%10s%20s%3s", "foo", "bar", "3"));

        assertThat(product.getName()).isEqualTo("foo");
        assertThat(product.getDescription()).isEqualTo("bar");
        assertThat(product.getAmountInStock()).isEqualTo(3);
    }

}