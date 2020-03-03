package be.kevinbaes.fixed_width_mapper.mapper;

public class FixedWidthStringFieldMetadata implements FixedWidthFieldMetadata<String> {
    private final String name;
    private final int width;

    public FixedWidthStringFieldMetadata(String name, int width) {
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
