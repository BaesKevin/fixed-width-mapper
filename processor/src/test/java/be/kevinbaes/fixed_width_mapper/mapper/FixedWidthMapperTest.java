package be.kevinbaes.fixed_width_mapper.mapper;

import be.kevinbaes.fixed_width_mapper.testmodel.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FixedWidthMapperTest {

    private FixedWidthMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new FixedWidthMapper();
    }

    @Test
    public void modelWithTwoSameLengthStringProperties_mapsCorrectly() {
        String name = "name";
        String description = "01234567890123456789";
        int stock = 3;
        String encoded = String.format("%10s%20s%3s", name, description, stock);

        FixedWidthMapper mapper = new FixedWidthMapper();
        Product product = mapper.stringToProduct(encoded);

        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getDescription()).isEqualTo(description);
        assertThat(product.getAmountInStock()).isEqualTo(stock);
    }

}