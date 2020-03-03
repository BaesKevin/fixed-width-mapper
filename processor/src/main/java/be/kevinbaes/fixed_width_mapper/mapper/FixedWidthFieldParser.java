package be.kevinbaes.fixed_width_mapper.mapper;

public interface FixedWidthFieldParser {
    void registerMapper(Class<?> type, DefaultFixedWidthFieldParser.Mapper<?> mapper);

    <T> T parseValue(String partOfEncodedString, Class<T> type);

    <T> T parseSingleField(String encoded, FixedWidthFieldMetadata<T> integerField);

    <T> T parseFieldFromObject(String partOfEncodedString, FixedWidthFieldMetadata<T> field, FlatFixedWidthObjectMetadata metadata);
}
