package be.kevinbaes.fixed_width_mapper.testmodel;

import be.kevinbaes.fixed_width_mapper.mapper.ProductMapper;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    public void parseString_returnsProduct() {
        Product product = new ProductMapper().fromString(format("%10s%20s%3s%5s%5s", "foo", "bar", "3", 5, 3));

        assertThat(product.getName()).isEqualTo("foo");
        assertThat(product.getDescription()).isEqualTo("bar");
        assertThat(product.getAmountInStock()).isEqualTo(3);
    }

}