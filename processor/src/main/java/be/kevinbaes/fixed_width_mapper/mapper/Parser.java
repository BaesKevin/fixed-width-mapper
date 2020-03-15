package be.kevinbaes.fixed_width_mapper.mapper;

import be.kevinbaes.fixed_width_mapper.mapper.metadata.Field;

public interface Parser {
    <T> T parseField(Field<T> field);

    int getParseableCharacters();
}
