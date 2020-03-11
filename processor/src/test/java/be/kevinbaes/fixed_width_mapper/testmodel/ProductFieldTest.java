package be.kevinbaes.fixed_width_mapper.testmodel;

import be.kevinbaes.fixed_width_mapper.mapper.metadata.Field;
import be.kevinbaes.fixed_width_mapper.testmodel.PriceInfo;
import be.kevinbaes.fixed_width_mapper.testmodel.Product;
import be.kevinbaes.fixed_width_mapper.testmodel.ProductField;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductFieldTest {
    @Test
    public void fromStringTest() {
        String name = "name";
        String description = "01234567890123456789";
        int stock = 3;
        int basePrice = 10;
        int discountPrice = 8;
        String encoded = String.format("%10s%20s%3s%5s%5s", name, description, stock, basePrice, discountPrice);

        Field<Product> field = new ProductField();
        Product product = field.parse(encoded);

        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getDescription()).isEqualTo(description);
        assertThat(product.getAmountInStock()).isEqualTo(stock);
        assertThat(product.getPriceInfo()).isEqualTo(new PriceInfo(basePrice, discountPrice));
    }

    @Test

    public void toStringTest() {
        String name = "name";
        String description = "01234567890123456789";
        int stock = 3;

        Product product = new Product(name, description, stock, new PriceInfo(10, 8));

        Field<Product> productField = new ProductField();

        String expectedEncoded = String.format("%10s%20s%3s%5s%5s", name, description, stock, 10, 8);
        assertThat(productField.toString(product)).isEqualTo(expectedEncoded);
    }

}