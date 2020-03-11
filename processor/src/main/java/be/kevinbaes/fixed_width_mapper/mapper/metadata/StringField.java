package be.kevinbaes.fixed_width_mapper.mapper.metadata;

public class StringField implements Field<String> {
    private final String name;
    private final int width;

    public StringField(String name, int width) {
        this.name = name;
        this.width = width;
    }

    @Override
    public String getName() { return name; }

    @Override
    public Field<String> setName(String name) {
        return new StringField(name, width);
    }

    @Override
    public int getWidth(String text) {
        return width;
    }

    @Override
    public String parse(String text) {
        return text;
    }

    @Override
    public String toString(String field) {
        return field;
    }
}
