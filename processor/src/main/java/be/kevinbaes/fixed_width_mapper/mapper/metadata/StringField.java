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
    public int getWidth() {
        return width;
    }
    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String parse(String text) {
        return text;
    }
}
