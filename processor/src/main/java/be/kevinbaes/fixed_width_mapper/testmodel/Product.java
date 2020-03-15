package be.kevinbaes.fixed_width_mapper.testmodel;

import be.kevinbaes.fixed_width_mapper.annotation.StringEncoded;

import java.util.Objects;

@StringEncoded
public class Product {

    private String name;
    private String description;
    private int amountInStock;
    private PriceInfo priceInfo;

    public Product(String name, String description, int amountInStock, PriceInfo priceInfo) {
        this.name = name;
        this.description = description;
        this.amountInStock = amountInStock;
        this.priceInfo = priceInfo;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getAmountInStock() {
        return amountInStock;
    }
    public PriceInfo getPriceInfo() { return priceInfo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return amountInStock == product.amountInStock &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(priceInfo, product.priceInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, amountInStock, priceInfo);
    }
}
