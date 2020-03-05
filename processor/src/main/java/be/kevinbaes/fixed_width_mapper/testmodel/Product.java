package be.kevinbaes.fixed_width_mapper.testmodel;

import be.kevinbaes.fixed_width_mapper.annotation.StringEncoded;

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
}
