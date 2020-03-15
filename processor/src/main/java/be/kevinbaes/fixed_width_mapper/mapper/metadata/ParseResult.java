package be.kevinbaes.fixed_width_mapper.mapper.metadata;

public class ParseResult<T> {
    private final T value;
    private final int charsRead;

    public ParseResult(T value, int charsRead) {
        this.value = value;
        this.charsRead = charsRead;
    }

    public T getValue() {
        return value;
    }

    public int getCharsRead() {
        return charsRead;
    }
}
