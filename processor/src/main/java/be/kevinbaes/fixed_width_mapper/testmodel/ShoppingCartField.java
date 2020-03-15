package be.kevinbaes.fixed_width_mapper.testmodel;

import be.kevinbaes.fixed_width_mapper.mapper.Parser;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.*;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;

public class ShoppingCartField extends CompositeField<ShoppingCart> {
    private static final StringField nameField= new StringField("name", 5);
    private static final LocalDateField creationDateField =  new LocalDateField("creationDate", "yyyy-MM-dd");

    private static final IntegerField productcounter = new IntegerField("productcount", 2);;
    private static final ProductField productfieldtemplate = new ProductField("product");
    private static final RepeatingFieldWithCounter<Product> productsField = new RepeatingFieldWithCounter<>("products", productcounter, productfieldtemplate);

    public ShoppingCartField(String name) {
        super(name,asList(nameField, productsField, creationDateField));
    }

    @Override
    public Field<ShoppingCart> setName(String name) {
        return new ShoppingCartField(name);
    }

    @Override
    public ParseResult<ShoppingCart> parseForParser(Parser parser) {
        List<Product> products = parser.getValueFor(productsField);
        LocalDate creationDate = parser.getValueFor(creationDateField);
        return new ParseResult<>(new ShoppingCart(getName().trim(), products, creationDate), parser.getParseableCharacters());
    }

    @Override
    public String toFullWidthString(ShoppingCart field) {
        return nameField.toFullWidthString(field.getName()) +
                productsField.toFullWidthString(field.getProducts()) +
                creationDateField.toFullWidthString(field.getCreationDate());
    }
}
