package be.kevinbaes.fixed_width_mapper.mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Objects.isNull;

public class DefaultFixedWidthFieldParser implements FixedWidthFieldParser {

    private Map<Class<?>, Mapper<?>> mappers;

    public DefaultFixedWidthFieldParser() {
        this.mappers = new HashMap<>();
    }

    @Override
    public void registerMapper(Class<?> type, Mapper<?> mapper) {
        if (!isNull(mappers.get(type))) {
            throw new IllegalArgumentException(format("type [%s] already registered", type.getName()));
        }

        mappers.put(type, mapper);
    }

    public static DefaultFixedWidthFieldParser withDefaultMappers() {
        DefaultFixedWidthFieldParser parser = new DefaultFixedWidthFieldParser();
        parser.registerMapper(Integer.class, new IntMapper());
        parser.registerMapper(String.class, string -> string);
        return parser;
    }

    @Override
    public <T> T parseValue(String partOfEncodedString, Class<T> type) {
        return (T) Optional.ofNullable(mappers.get(type))
                .map(m -> m.map(partOfEncodedString))
                .orElseThrow(() -> new IllegalArgumentException(format("type [%s] not supported", type.getName())));
    }

    @Override
    public <T> T parseSingleField(String encoded, FixedWidthFieldMetadata<T> field) {
        return parseValue(encoded, field.getTargetType());
    }

    @Override
    public <T> T parseFieldFromObject(String partOfEncodedString, FixedWidthFieldMetadata<T> field, FlatFixedWidthObjectMetadata metadata) {
        int nameStart = metadata.getStartingPosition(field);
        int nameEnd = nameStart + field.getWidth();
        String partAsString = partOfEncodedString.substring(nameStart, nameEnd);
        return parseValue(partAsString, field.getTargetType());
    }

    interface Mapper<T> {
        T map(String value);
    }

    static class IntMapper implements Mapper<Integer> {
        @Override
        public Integer map(String value) {
            return Integer.parseInt(value.trim());
        }
    }

}
