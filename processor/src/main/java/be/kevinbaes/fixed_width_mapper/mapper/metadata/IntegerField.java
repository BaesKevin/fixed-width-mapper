package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import static java.lang.String.format;

public class IntegerField implements Field<Integer> {
    private String name;
    private int width;

    public IntegerField(String name, int width) {
        this.name = name;
        this.width = width;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Field<Integer> setName(String name) {
        return new IntegerField(name, width);
    }

    @Override
    public ParseResult<Integer> parseWithResult(String s) {
        int end = width;

        if (s.length() < end) {
            end = s.length();
        }

        String substring = s.substring(0, end);
        return new ParseResult<>(Integer.valueOf(substring.trim()), width);
    }

    @Override
    public String toFullWidthString(Integer number) {
        return format("%" + width + "d", number);
    }

}
