package be.kevinbaes.fixed_width_mapper.mapper;

import be.kevinbaes.fixed_width_mapper.mapper.metadata.FieldMetadata;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.FlatObjectMetadata;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Objects.isNull;

public class DefaultParser implements Parser {

    private Map<Class<?>, FieldMapper<?>> mappers;

    public DefaultParser() {
        this.mappers = new HashMap<>();
    }

    public static DefaultParser withDefaultMappers() {
        DefaultParser parser = new DefaultParser();
        parser.registerMapper(Integer.class, new IntMapper());
        parser.registerMapper(String.class, string -> string);
        return parser;
    }

    @Override
    public void registerMapper(Class<?> type, FieldMapper<?> mapper) {
        if (!isNull(mappers.get(type))) {
            throw new IllegalArgumentException(format("type [%s] already registered", type.getName()));
        }

        mappers.put(type, mapper);
    }

    @Override
    public <T> T parseValue(String partOfEncodedString, Class<T> type) {
        return (T) Optional.ofNullable(mappers.get(type))
                .map(m -> m.map(partOfEncodedString))
                .orElseThrow(() -> new IllegalArgumentException(format("type [%s] not supported", type.getName())));
    }

    @Override
    public <T> T parseSingleField(String encoded, FieldMetadata<T> field) {
        return parseValue(encoded, field.getTargetType());
    }

    @Override
    public <T> T parseFieldFromObject(String partOfEncodedString, FieldMetadata<T> field, FlatObjectMetadata metadata) {
        int nameStart = metadata.getStartingPosition(field);
        int nameEnd = nameStart + field.getWidth();
        String partAsString = partOfEncodedString.substring(nameStart, nameEnd);
        return parseValue(partAsString, field.getTargetType());
    }

    static class IntMapper implements FieldMapper<Integer> {
        @Override
        public Integer map(String value) {
            return Integer.parseInt(value.trim());
        }
    }

}
