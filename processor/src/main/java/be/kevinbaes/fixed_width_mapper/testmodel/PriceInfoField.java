package be.kevinbaes.fixed_width_mapper.testmodel;

import be.kevinbaes.fixed_width_mapper.mapper.Parser;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.CompositeField;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.Field;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.IntegerField;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.ParseResult;

import static java.util.Arrays.asList;

public class PriceInfoField extends CompositeField<PriceInfo> {
    public static final Field<Integer> DISCOUNT_PRICE = new IntegerField("discount price", 5);
    public static final Field<Integer> BASE_PRICE = new IntegerField("base price", 5);

    public PriceInfoField(String name) {
        super(name, asList(BASE_PRICE, DISCOUNT_PRICE));
    }

    @Override
    public Field<PriceInfo> setName(String name) {
        return new PriceInfoField(name);
    }

    @Override
    public ParseResult<PriceInfo> parseForParser(Parser parser) {
        int basePrice = parser.getValueFor(BASE_PRICE);
        int discountPrice = parser.getValueFor(DISCOUNT_PRICE);
        return new ParseResult<>(new PriceInfo(basePrice, discountPrice), parser.getParseableCharacters());
    }

    @Override
    public String toFullWidthString(PriceInfo field) {
        return BASE_PRICE.toFullWidthString(field.getBasePrice()) + DISCOUNT_PRICE.toFullWidthString(field.getDiscountPrice());
    }

}
