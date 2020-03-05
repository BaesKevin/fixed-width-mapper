package be.kevinbaes.fixed_width_mapper.mapper;

import be.kevinbaes.fixed_width_mapper.mapper.metadata.FieldMetadata;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.ObjectMetadata;

import java.util.*;

import static java.lang.String.format;
import static java.util.Objects.*;
import static java.util.Objects.isNull;

public class DefaultParser implements Parser {

    private String encodedString;
    private ObjectMetadata objectMetadata;
    private Map<Class<?>, FieldMapper<?>> mappers;

    public DefaultParser(String encodedString, ObjectMetadata objectMetadata) {
        this.encodedString = requireNonNull(encodedString);
        this.objectMetadata = objectMetadata;
        this.mappers = new HashMap<>();
    }

    public static DefaultParserBuilder builder() {
        return new DefaultParserBuilder();
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
    public <T> T parseSingleField(FieldMetadata<T> field) {
        return parseValue(encodedString, field.getTargetType());
    }

    @Override
    public <T> T parseFieldFromObject(FieldMetadata<T> field) {
        int nameStart = objectMetadata.getStartingPosition(field);
        int nameEnd = nameStart + field.getWidth();
        String partAsString = encodedString.substring(nameStart, nameEnd);
        return parseValue(partAsString, field.getTargetType());
    }

    public DefaultParserBuilder toBuilder() {
        return builder()
                .withDefaultMappers() // TODO withMappers method
                .withEncodedString(encodedString)
                .withObjectMetadata(objectMetadata);
    }

    public static class DefaultParserBuilder {

        private Map<Class<?>, FieldMapper<?>> mappers = new HashMap<>();
        private String encodedString;
        private ObjectMetadata objectMetadata;

        public DefaultParserBuilder withDefaultMappers() {
            mappers.put(Integer.class, value -> Integer.parseInt(value.trim()));
            mappers.put(String.class, string -> string);
            return this;
        }

        public DefaultParserBuilder withEncodedString(String encodedString) {
            this.encodedString = encodedString;
            return this;
        }

        public DefaultParserBuilder withObjectMetadata(ObjectMetadata objectMetadata) {
            this.objectMetadata = objectMetadata;
            return this;
        }

        public DefaultParser build() {
            if (isNull(encodedString)) {
                this.encodedString = "";
            }

            DefaultParser defaultParser = new DefaultParser(encodedString, objectMetadata);
            mappers.forEach(defaultParser::registerMapper);

            return defaultParser;
        }

    }

}
