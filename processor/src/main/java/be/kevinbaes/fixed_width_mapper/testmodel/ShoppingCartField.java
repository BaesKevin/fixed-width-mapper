package be.kevinbaes.fixed_width_mapper.testmodel;

import be.kevinbaes.fixed_width_mapper.mapper.DefaultParser;
import be.kevinbaes.fixed_width_mapper.mapper.Parser;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.*;

import java.time.LocalDate;
import java.util.List;

public class ShoppingCartField implements Field<ShoppingCart> {
    private final StringField nameField;
    private final LocalDateField creationDateField;
    private String name;

    private final IntegerField productcounter;
    private final ProductField productfieldtemplate;
    private final RepeatingField<Product> productsField;

    public ShoppingCartField(String name) {
        this.name = name;

        productcounter = new IntegerField("productcount", 2);
        productfieldtemplate = new ProductField("product");
        productsField = new RepeatingField<>("products", productcounter, productfieldtemplate);
        nameField = new StringField("name", 5);
        creationDateField = new LocalDateField("creationDate", "yyyy-MM-dd");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Field<ShoppingCart> setName(String name) {
        return new ShoppingCartField(name);
    }

    @Override
    public ParseResult<ShoppingCart> parseWithResult(String s) {
        Parser parser = DefaultParser.builder().withEncodedString(s).withFields(nameField, productsField, creationDateField).build();
        String name = parser.getValueFor(nameField);
        List<Product> products = parser.getValueFor(productsField);
        LocalDate creationDate = parser.getValueFor(creationDateField);
        return new ParseResult<>(new ShoppingCart(name.trim(), products, creationDate), parser.getParseableCharacters());
    }

    @Override
    public String toFullWidthString(ShoppingCart field) {
        return nameField.toFullWidthString(field.getName()) +
                productsField.toFullWidthString(field.getProducts()) +
                creationDateField.toFullWidthString(field.getCreationDate());
    }
}
