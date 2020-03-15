package be.kevinbaes.fixed_width_mapper.testmodel;

import be.kevinbaes.fixed_width_mapper.mapper.DefaultParser;
import be.kevinbaes.fixed_width_mapper.mapper.Parser;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.*;

public class ProductField implements Field<Product> {
    private final Field<String> NAME = new StringField("name", 10);
    private final Field<String> DESCRIPTION = new StringField("description",20);
    private final Field<Integer> STOCK = new IntegerField("amount in stock",3);
    private final Field<PriceInfo> PRICE_INFO = new PriceInfoField("priceinfo");

    private final Fields PRODUCT_METADATA = Fields.builder()
            .addField(NAME)
            .addField(DESCRIPTION)
            .addField(STOCK)
            .addField(PRICE_INFO)
            .build();

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
        Parser parser = DefaultParser.builder().withEncodedString(s).withFields(PRODUCT_METADATA).build();
        String name = parser.parseField(NAME);
        int amountInStock = parser.parseField(STOCK);
        String description = parser.parseField(DESCRIPTION);
        PriceInfo priceInfo = parser.parseField(PRICE_INFO);

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
