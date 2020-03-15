package be.kevinbaes.fixed_width_mapper.mapper.metadata;

public abstract class FixedWidthField<T> implements Field<T> {

    private final int width;
    private String name;

    public FixedWidthField(String name, int width) {
        this.name = name;
        this.width = width;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public abstract Field<T> setName(String name);

    public ParseResult<T> parseWithResult(String s) {
        int end = getWidth();

        if (s.length() < end) {
            end = s.length();
        }

        return new ParseResult<>(parseParseablePart(s.substring(0, end)), end);
    }

    public abstract T parseParseablePart(String parseablePart);

    @Override
    public String toFullWidthString(T field) {
        return String.format("%" + width + "s", field);
    }
}
