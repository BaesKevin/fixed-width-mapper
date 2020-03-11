package be.kevinbaes.fixed_width_mapper.testmodel;

import be.kevinbaes.fixed_width_mapper.mapper.DefaultParser;
import be.kevinbaes.fixed_width_mapper.mapper.Parser;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.Field;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.Fields;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.IntegerField;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.StringField;

import java.util.stream.Collectors;

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


    @Override
    public Product parse(String text) {
        Parser parser = DefaultParser.builder().withEncodedString(text).withFields(PRODUCT_METADATA).build();
        String name = parser.parseField(NAME);
        int amountInStock = parser.parseField(STOCK);
        String description = parser.parseField(DESCRIPTION);
        PriceInfo priceInfo = parser.parseField(PRICE_INFO);

        return new Product(name.trim(), description.trim(), amountInStock, priceInfo);
    }

    @Override
    public String getName() {
        return "foo";
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public String toString(Product product) {
        String formatString = getFormatString();
        return String.format(formatString,
                product.getName(),
                product.getDescription(),
                product.getAmountInStock(),
                PRICE_INFO.toString(product.getPriceInfo()));
    }

    private String getFormatString() {
        return PRODUCT_METADATA.fields().stream()
                .map(field -> String.format("%%%ds", field.getWidth()))
                .collect(Collectors.joining());
    }

}
