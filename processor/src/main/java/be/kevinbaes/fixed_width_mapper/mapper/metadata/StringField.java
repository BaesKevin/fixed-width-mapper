package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import static java.lang.String.format;

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

    public int getWidth() {
        return width;
    }

    @Override
    public ParseResult<String> parseWithResult(String s) {
        return new ParseResult<>(s.substring(0, getWidth()), getWidth());
    }

    @Override
    public String toFullWidthString(String field) {
        return format("%" + width + "s", field);
    }

}
