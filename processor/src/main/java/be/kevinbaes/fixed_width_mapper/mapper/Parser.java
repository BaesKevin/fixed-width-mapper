package be.kevinbaes.fixed_width_mapper.mapper;

import be.kevinbaes.fixed_width_mapper.mapper.metadata.FieldMetadata;

public interface Parser {
    void registerMapper(Class<?> type, FieldMapper<?> mapper);

    <T> T parseValue(String partOfEncodedString, Class<T> type);

    <T> T parseSingleField(FieldMetadata<T> field);

    <T> T parseFieldFromObject(FieldMetadata<T> field);

}
