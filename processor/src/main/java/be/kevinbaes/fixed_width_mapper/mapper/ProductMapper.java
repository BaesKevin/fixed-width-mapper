package be.kevinbaes.fixed_width_mapper.mapper;

import be.kevinbaes.fixed_width_mapper.mapper.metadata.FieldMetadata;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.ObjectMetadata;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.IntegerFieldMetadata;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.StringFieldMetadata;
import be.kevinbaes.fixed_width_mapper.testmodel.PriceInfo;
import be.kevinbaes.fixed_width_mapper.testmodel.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper implements ObjectMapper<Product> {

    public static final IntegerFieldMetadata DISCOUNT_PRICE = new IntegerFieldMetadata("discount price", 5);
    public static final IntegerFieldMetadata BASE_PRICE = new IntegerFieldMetadata("base price", 5);
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

    private Parser parser;

    public ProductMapper() {
        parser = DefaultParser.withDefaultMappers();
    }

    @Override
    public Product fromString(String string) {
        String name = parser.parseFieldFromObject(string, NAME_FIELD, PRODUCT_METADATA);
        String description = parser.parseFieldFromObject(string, DESCRIPTION_FIELD, PRODUCT_METADATA);
        int amountInStock = parser.parseFieldFromObject(string, STOCK_FIELD, PRODUCT_METADATA);
        int basePrice = parser.parseFieldFromObject(string, BASE_PRICE, PRODUCT_METADATA);
        int discountPrice = parser.parseFieldFromObject(string, DISCOUNT_PRICE, PRODUCT_METADATA);

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
        List<Integer> widths = new ArrayList<>();

        widths.add(NAME_FIELD.getWidth());
        widths.add(DESCRIPTION_FIELD.getWidth());
        widths.add(STOCK_FIELD.getWidth());
        widths.add(BASE_PRICE.getWidth());
        widths.add(DISCOUNT_PRICE.getWidth());

        return widths.stream()
                .map(width -> String.format("%%%ds", width))
                .collect(Collectors.joining());
    }

}
