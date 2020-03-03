package be.kevinbaes.fixed_width_mapper.testmodel;

import be.kevinbaes.fixed_width_mapper.annotation.StringEncoded;
import be.kevinbaes.fixed_width_mapper.mapper.*;

@StringEncoded
public class Product {

    private String name;
    private String description;
    private int amountInStock;
    private FixedWidthFieldParser fixedWidthFieldParser;

    private static final FixedWidthFieldMetadata<String> NAME_FIELD = new FixedWidthStringFieldMetadata("name", 10);
    private static final FixedWidthFieldMetadata<String> DESCRIPTION_FIELD = new FixedWidthStringFieldMetadata("description",20);
    private static final FixedWidthFieldMetadata<Integer> STOCK_FIELD = new FixedWidthIntegerFieldMetadata("amount in stock",3);

    private static final FlatFixedWidthObjectMetadata OBJECT_METADATA = FlatFixedWidthObjectMetadata.builder()
            .add(NAME_FIELD)
            .add(DESCRIPTION_FIELD)
            .add(STOCK_FIELD)
            .build();

    public Product() {
        this("", "", 0);
    }

    public Product(String name, String description, int amountInStock) {
        this.name = name;
        this.description = description;
        this.amountInStock = amountInStock;
        fixedWidthFieldParser = DefaultFixedWidthFieldParser.withDefaultMappers();
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public Product fromFixedWidthString(String string) {
        String name = fixedWidthFieldParser.parseFieldFromObject(string, NAME_FIELD, OBJECT_METADATA);
        String description = fixedWidthFieldParser.parseFieldFromObject(string, DESCRIPTION_FIELD, OBJECT_METADATA);
        int amountInStock = fixedWidthFieldParser.parseFieldFromObject(string, STOCK_FIELD, OBJECT_METADATA);

        return new Product(name.trim(), description.trim(), amountInStock);
    }

    public int getAmountInStock() {
        return amountInStock;
    }
}
