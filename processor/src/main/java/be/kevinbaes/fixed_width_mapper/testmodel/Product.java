package be.kevinbaes.fixed_width_mapper.testmodel;

import be.kevinbaes.fixed_width_mapper.annotation.StringEncoded;

@StringEncoded
public class Product {

    private String name;
    private String description;
    private int amountInStock;

    public Product(String name, String description, int amountInStock) {
        this.name = name;
        this.description = description;
        this.amountInStock = amountInStock;
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

}
