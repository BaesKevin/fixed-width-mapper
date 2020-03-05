package be.kevinbaes.fixed_width_mapper.mapper;

import be.kevinbaes.fixed_width_mapper.mapper.metadata.FieldMetadata;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.ObjectMetadata;

public interface Parser {
    void registerMapper(Class<?> type, FieldMapper<?> mapper);

    <T> T parseValue(String partOfEncodedString, Class<T> type);

    <T> T parseSingleField(String encoded, FieldMetadata<T> integerField);

    <T> T parseFieldFromObject(String partOfEncodedString, FieldMetadata<T> field, ObjectMetadata metadata);
}
