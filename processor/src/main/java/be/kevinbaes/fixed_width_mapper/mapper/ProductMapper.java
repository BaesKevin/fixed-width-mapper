package be.kevinbaes.fixed_width_mapper.mapper;

import be.kevinbaes.fixed_width_mapper.mapper.metadata.FieldMetadata;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.IntegerFieldMetadata;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.ObjectMetadata;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.StringFieldMetadata;
import be.kevinbaes.fixed_width_mapper.testmodel.PriceInfo;
import be.kevinbaes.fixed_width_mapper.testmodel.Product;

import java.util.stream.Collectors;

public class ProductMapper implements ObjectMapper<Product> {

    public static final FieldMetadata<Integer> DISCOUNT_PRICE = new IntegerFieldMetadata("discount price", 5);
    public static final FieldMetadata<Integer> BASE_PRICE = new IntegerFieldMetadata("base price", 5);
    private final FieldMetadata<String> NAME_FIELD = new StringFieldMetadata("name", 10);
    private final FieldMetadata<String> DESCRIPTION_FIELD = new StringFieldMetadata("description",20);
    private final FieldMetadata<Integer> STOCK_FIELD = new IntegerFieldMetadata("amount in stock",3);

    private final ObjectMetadata PRICE_INFO_METADATA = ObjectMetadata.builder()
            .addField(BASE_PRICE)
            .addField(DISCOUNT_PRICE)
            .build();

    private final ObjectMetadata PRODUCT_METADATA = ObjectMetadata.builder()
            .addField(NAME_FIELD)
            .addField(DESCRIPTION_FIELD)
            .addField(STOCK_FIELD)
            .addObject(PRICE_INFO_METADATA)
            .build();

    private DefaultParser parser;

    public ProductMapper() {
        parser = DefaultParser.builder()
                .withDefaultMappers()
                .withObjectMetadata(PRODUCT_METADATA)
                .build();
    }

    @Override
    public Product fromString(String string) {
        parser = parser.toBuilder().withEncodedString(string).build();
        String name = parser.parseFieldFromObject(NAME_FIELD);
        int amountInStock = parser.parseFieldFromObject(STOCK_FIELD);
        String description = parser.parseFieldFromObject(DESCRIPTION_FIELD);
        int basePrice = parser.parseFieldFromObject(BASE_PRICE);
        int discountPrice = parser.parseFieldFromObject(DISCOUNT_PRICE);

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
