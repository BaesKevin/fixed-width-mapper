package be.kevinbaes.fixed_width_mapper.mapper;

import be.kevinbaes.fixed_width_mapper.testmodel.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperTest {

    private ProductMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ProductMapper();
    }

    @Test
    public void fromStringTest() {
        String name = "name";
        String description = "01234567890123456789";
        int stock = 3;
        String encoded = String.format("%10s%20s%3s", name, description, stock);

        ProductMapper mapper = new ProductMapper();
        Product product = mapper.fromString(encoded);

        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getDescription()).isEqualTo(description);
        assertThat(product.getAmountInStock()).isEqualTo(stock);
    }

    @Test
    public void toStringTest() {
        String name = "name";
        String description = "01234567890123456789";
        int stock = 3;

        Product product = new Product(name, description, stock);

        ObjectMapper<Product> productMapper = new ProductMapper();

        String expectedEncoded = String.format("%10s%20s%3s", name, description, stock);
        assertThat(productMapper.toString(product)).isEqualTo(expectedEncoded);
    }

}