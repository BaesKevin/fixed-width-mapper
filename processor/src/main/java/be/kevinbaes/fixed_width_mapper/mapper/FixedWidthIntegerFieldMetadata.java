package be.kevinbaes.fixed_width_mapper.mapper;

public class FixedWidthIntegerFieldMetadata implements FixedWidthFieldMetadata<Integer> {
    private String name;
    private int width;

    public FixedWidthIntegerFieldMetadata(String name, int width) {
        this.name = name;
        this.width = width;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public Class<Integer> getTargetType() {
        return Integer.class;
    }
}
