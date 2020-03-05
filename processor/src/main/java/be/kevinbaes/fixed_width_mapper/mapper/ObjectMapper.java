package be.kevinbaes.fixed_width_mapper.mapper;

import be.kevinbaes.fixed_width_mapper.testmodel.Product;

public interface ObjectMapper<T> {
    T fromString(String string);
    String toString(Product product);
}
