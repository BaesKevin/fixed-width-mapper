package be.kevinbaes.fixed_width_mapper.mapper.metadata;

public interface Field<T> {
    String getName();
    Field<T> setName(String name);

    default T parse(String text) { return parseWithResult(text).getValue(); };
    ParseResult<T> parseWithResult(String s);
    String toFullWidthString(T field);

    default boolean isEqualTo(Field<?> otherField) {
        return hasName(otherField.getName());
    }
    default boolean hasName(String name) {
        return getName().equals(name);
    }
}
