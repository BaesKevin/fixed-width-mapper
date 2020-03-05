package be.kevinbaes.fixed_width_mapper.mapper;

public interface FieldMapper<T> {

    T map(String value);

}
