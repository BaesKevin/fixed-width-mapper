package be.kevinbaes.fixed_width_mapper.testmodel;

import be.kevinbaes.fixed_width_mapper.mapper.DefaultParser;
import be.kevinbaes.fixed_width_mapper.mapper.Parser;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.Field;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.Fields;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.IntegerField;

import java.util.stream.Collectors;

public class PriceInfoField implements Field<PriceInfo> {
    private String name;

    public static final Field<Integer> DISCOUNT_PRICE = new IntegerField("discount price", 5);
    public static final Field<Integer> BASE_PRICE = new IntegerField("base price", 5);

    private final Fields PRICE_INFO_METADATA = Fields.builder()
            .addField(BASE_PRICE)
            .addField(DISCOUNT_PRICE)
            .build();


    public PriceInfoField(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Field<PriceInfo> setName(String name) {
        return new PriceInfoField(name);
    }

    @Override
    public int getWidth(String text) {
        return DISCOUNT_PRICE.getWidth(text) + BASE_PRICE.getWidth(text);
    }

    @Override
    public PriceInfo parse(String text) {
        Parser parser = DefaultParser.builder().withEncodedString(text).withFields(PRICE_INFO_METADATA).build();
        int basePrice = parser.parseField(BASE_PRICE);
        int discountPrice = parser.parseField(DISCOUNT_PRICE);
        return new PriceInfo(basePrice, discountPrice);
    }

    @Override
    public String toString(PriceInfo field) {
        String formatString = getFormatString();
        return String.format(formatString,
                field.getBasePrice(),
                field.getDiscountPrice());
    }

    private String getFormatString() {
        return PRICE_INFO_METADATA.fields().stream()
                .map(field -> String.format("%%%ds", field.getWidth("")))
                .collect(Collectors.joining());
    }
}
