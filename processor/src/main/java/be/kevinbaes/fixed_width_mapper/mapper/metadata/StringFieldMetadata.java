package be.kevinbaes.fixed_width_mapper.mapper.metadata;

public class StringFieldMetadata implements FieldMetadata<String> {
    private final String name;
    private final int width;

    public StringFieldMetadata(String name, int width) {
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

}
