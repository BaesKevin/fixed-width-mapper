package be.kevinbaes.fixed_width_mapper.mapper.metadata;

public class StringField extends FixedWidthField<String> {
    public StringField(String name, int width) {
        super(name, width);
    }

    @Override
    public Field<String> setName(String name) {
        return new StringField(name, getWidth());
    }

    @Override
    public String parseParseablePart(String parseablePart) {
        return parseablePart;
    }

}
