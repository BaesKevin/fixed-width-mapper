package be.kevinbaes.fixed_width_mapper.testmodel;

import be.kevinbaes.fixed_width_mapper.mapper.DefaultParser;
import be.kevinbaes.fixed_width_mapper.mapper.Parser;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.*;

public class ProductField implements Field<Product> {
    private final Field<String> NAME = new StringField("name", 10);
    private final Field<String> DESCRIPTION = new StringField("description",20);
    private final Field<Integer> STOCK = new IntegerField("amount in stock",3);
    private final Field<PriceInfo> PRICE_INFO = new PriceInfoField("priceinfo");

    private String name;

    public ProductField(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Field<Product> setName(String name) {
        return new ProductField(name);
    }

    @Override
    public ParseResult<Product> parseWithResult(String s) {
        Parser parser = DefaultParser.builder().withEncodedString(s).withFields(NAME, DESCRIPTION, STOCK, PRICE_INFO).build();
        String name = parser.getValueFor(NAME);
        int amountInStock = parser.getValueFor(STOCK);
        String description = parser.getValueFor(DESCRIPTION);
        PriceInfo priceInfo = parser.getValueFor(PRICE_INFO);

        return new ParseResult<>(new Product(name.trim(), description.trim(), amountInStock, priceInfo), parser.getParseableCharacters());
    }

    @Override
    public String toFullWidthString(Product product) {
        return NAME.toFullWidthString(product.getName()) +
                DESCRIPTION.toFullWidthString(product.getDescription()) +
                STOCK.toFullWidthString(product.getAmountInStock()) +
                PRICE_INFO.toFullWidthString(product.getPriceInfo());
    }

}
