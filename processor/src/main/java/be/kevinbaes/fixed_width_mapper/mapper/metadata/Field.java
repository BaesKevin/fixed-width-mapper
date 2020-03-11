package be.kevinbaes.fixed_width_mapper.mapper.metadata;

public interface Field<T> {
    String getName();

    int getWidth();

    Class<T> getTargetType();

    default boolean isEqualTo(Field<?> otherField) {
        return hasName(otherField.getName());
    }

    default boolean hasName(String name) {
        return getName().equals(name);
    }

    T parse(String text);
}
