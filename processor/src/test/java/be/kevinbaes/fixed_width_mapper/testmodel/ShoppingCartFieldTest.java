package be.kevinbaes.fixed_width_mapper.testmodel;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ShoppingCartFieldTest {

    @Test
    void parse() {
        ShoppingCartField cartField = new ShoppingCartField("cart");

        Product product1 = new Product("product1", "desc", 3, new PriceInfo(3, 4));
        Product product2 = new Product("product2", "desc2", 4, new PriceInfo(5, 6));
        List<Product> products = Arrays.asList(product1, product2);
        ShoppingCart cart = new ShoppingCart("cart", products, LocalDate.now());

        String text = cartField.toFullWidthString(cart);
        assertThat(cartField.parse(text)).isEqualTo(cart);
    }
}