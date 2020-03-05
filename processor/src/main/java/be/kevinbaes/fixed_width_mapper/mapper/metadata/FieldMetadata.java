package be.kevinbaes.fixed_width_mapper.mapper.metadata;

public interface FieldMetadata<T> {
    String getName();

    int getWidth();

    Class<T> getTargetType();
}
