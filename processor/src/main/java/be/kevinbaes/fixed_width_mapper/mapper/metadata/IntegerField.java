package be.kevinbaes.fixed_width_mapper.mapper.metadata;

public class IntegerField extends FixedWidthField<Integer> {
    public IntegerField(String name, int width) {
        super(name, width);
    }

    @Override
    public Field<Integer> setName(String name) {
        return new IntegerField(name, getWidth());
    }

    @Override
    public Integer parseParseablePart(String parseablePart) {
        return Integer.valueOf(parseablePart.trim());
    }

}
