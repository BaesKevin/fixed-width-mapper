package be.kevinbaes.fixed_width_mapper.mapper.metadata;

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
    public int getWidth() {
        return width;
    }

    @Override
    public Integer parse(String text) {
        return Integer.parseInt(text.trim());
    }

    @Override
    public String toString(Integer number) {
        return "" + number;
    }
}
