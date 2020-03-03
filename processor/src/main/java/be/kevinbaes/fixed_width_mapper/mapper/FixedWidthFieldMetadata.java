package be.kevinbaes.fixed_width_mapper.mapper;

public interface FixedWidthFieldMetadata<T> {
    String getName();

    int getWidth();

    Class<T> getTargetType();
}
