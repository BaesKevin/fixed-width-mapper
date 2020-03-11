package be.kevinbaes.fixed_width_mapper.mapper.metadata;

public interface Field<T> {
    String getName();

    Field<T> setName(String name);

    int getWidth(String text);
    T parse(String text);

    String toString(T field);

    default boolean isEqualTo(Field<?> otherField) {
        return hasName(otherField.getName());
    }

    default boolean hasName(String name) {
        return getName().equals(name);
    }
}
