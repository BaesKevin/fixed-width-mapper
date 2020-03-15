package be.kevinbaes.fixed_width_mapper.testmodel;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ShoppingCart {

    private String name;
    private List<Product> products;
    private LocalDate creationDate;

    public ShoppingCart(String name, List<Product> products, LocalDate creationDate) {
        this.name = name;
        this.products = products;
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(products, that.products) &&
                Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, products, creationDate);
    }
}
