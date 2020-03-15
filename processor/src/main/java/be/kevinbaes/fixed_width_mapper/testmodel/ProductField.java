package be.kevinbaes.fixed_width_mapper.testmodel;

import be.kevinbaes.fixed_width_mapper.mapper.CachedParser;
import be.kevinbaes.fixed_width_mapper.mapper.Parser;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.*;

import static java.util.Arrays.asList;

public class ProductField extends CompositeField<Product> {
    private static final Field<String> NAME = new StringField("name", 10);
    private static final Field<String> DESCRIPTION = new StringField("description",20);
    private static final Field<Integer> STOCK = new IntegerField("amount in stock",3);
    private static final Field<PriceInfo> PRICE_INFO = new PriceInfoField("priceinfo");

    public ProductField(String name) {
        super(name, asList(NAME, DESCRIPTION, STOCK, PRICE_INFO));
    }

    @Override
    public Field<Product> setName(String name) {
        return new ProductField(name);
    }

    @Override
    public ParseResult<Product> parseForParser(Parser parser) {
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
