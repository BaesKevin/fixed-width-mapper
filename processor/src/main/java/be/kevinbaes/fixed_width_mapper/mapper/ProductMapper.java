package be.kevinbaes.fixed_width_mapper.mapper;

import be.kevinbaes.fixed_width_mapper.mapper.metadata.Field;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.IntegerField;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.Fields;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.StringField;
import be.kevinbaes.fixed_width_mapper.testmodel.PriceInfo;
import be.kevinbaes.fixed_width_mapper.testmodel.Product;

import java.util.stream.Collectors;

public class ProductMapper implements ObjectMapper<Product> {

    public static final Field<Integer> DISCOUNT_PRICE = new IntegerField("discount price", 5);
    public static final Field<Integer> BASE_PRICE = new IntegerField("base price", 5);
    private final Field<String> NAME_FIELD = new StringField("name", 10);
    private final Field<String> DESCRIPTION_FIELD = new StringField("description",20);
    private final Field<Integer> STOCK_FIELD = new IntegerField("amount in stock",3);

    private final Fields PRICE_INFO_METADATA = Fields.builder()
            .addField(BASE_PRICE)
            .addField(DISCOUNT_PRICE)
            .build();

    private final Fields PRODUCT_METADATA = Fields.builder()
            .addField(NAME_FIELD)
            .addField(DESCRIPTION_FIELD)
            .addField(STOCK_FIELD)
            .addObject(PRICE_INFO_METADATA)
            .build();

    private DefaultParser parser;

    public ProductMapper() {
        parser = DefaultParser.builder()
                .withFields(PRODUCT_METADATA)
                .build();
    }

    @Override
    public Product fromString(String string) {
        parser = parser.toBuilder().withEncodedString(string).build();
        String name = parser.parseField(NAME_FIELD);
        int amountInStock = parser.parseField(STOCK_FIELD);
        String description = parser.parseField(DESCRIPTION_FIELD);
        int basePrice = parser.parseField(BASE_PRICE);
        int discountPrice = parser.parseField(DISCOUNT_PRICE);

        return new Product(name.trim(), description.trim(), amountInStock, new PriceInfo(basePrice, discountPrice));
    }

    @Override
    public String toString(Product product) {
        String formatString = getFormatString();
        return String.format(formatString,
                product.getName(),
                product.getDescription(),
                product.getAmountInStock(),
                product.getPriceInfo().getBasePrice(),
                product.getPriceInfo().getDiscountPrice());
    }

    private String getFormatString() {
        return PRODUCT_METADATA.fields().stream()
                .map(field -> String.format("%%%ds", field.getWidth()))
                .collect(Collectors.joining());
    }

}
