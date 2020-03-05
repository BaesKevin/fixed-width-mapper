package be.kevinbaes.fixed_width_mapper.mapper;

import be.kevinbaes.fixed_width_mapper.mapper.metadata.FieldMetadata;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.FlatObjectMetadata;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.IntegerFieldMetadata;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.StringFieldMetadata;
import be.kevinbaes.fixed_width_mapper.testmodel.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper implements ObjectMapper<Product> {

    private final FieldMetadata<String> NAME_FIELD = new StringFieldMetadata("name", 10);
    private final FieldMetadata<String> DESCRIPTION_FIELD = new StringFieldMetadata("description",20);
    private final FieldMetadata<Integer> STOCK_FIELD = new IntegerFieldMetadata("amount in stock",3);

    private final FlatObjectMetadata OBJECT_METADATA = FlatObjectMetadata.builder()
            .add(NAME_FIELD)
            .add(DESCRIPTION_FIELD)
            .add(STOCK_FIELD)
            .build();

    private Parser parser;

    public ProductMapper() {
        parser = DefaultParser.withDefaultMappers();
    }

    @Override
    public Product fromString(String string) {
        String name = parser.parseFieldFromObject(string, NAME_FIELD, OBJECT_METADATA);
        String description = parser.parseFieldFromObject(string, DESCRIPTION_FIELD, OBJECT_METADATA);
        int amountInStock = parser.parseFieldFromObject(string, STOCK_FIELD, OBJECT_METADATA);

        return new Product(name.trim(), description.trim(), amountInStock);
    }

    @Override
    public String toString(Product product) {
        String formatString = getFormatString();
        return String.format(formatString, product.getName(), product.getDescription(), product.getAmountInStock());
    }

    private String getFormatString() {
        List<Integer> widths = new ArrayList<>();

        widths.add(NAME_FIELD.getWidth());
        widths.add(DESCRIPTION_FIELD.getWidth());
        widths.add(STOCK_FIELD.getWidth());

        return widths.stream()
                .map(width -> String.format("%%%ds", width))
                .collect(Collectors.joining());
    }

}
