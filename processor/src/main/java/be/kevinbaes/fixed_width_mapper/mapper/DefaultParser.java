package be.kevinbaes.fixed_width_mapper.mapper;

import be.kevinbaes.fixed_width_mapper.mapper.metadata.Field;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.Fields;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

public class DefaultParser implements Parser {

    private String encodedString;
    private Fields fields;

    public DefaultParser(String encodedString, Fields fields) {
        this.encodedString = requireNonNull(encodedString);
        this.fields = fields;
    }

    public static DefaultParserBuilder builder() {
        return new DefaultParserBuilder();
    }

    @Override
    public <T> T parseField(Field<T> field) {
        String fieldAsText = fields.getFieldAsText(encodedString, field.getName());
        return field.parse(fieldAsText);
    }

    public DefaultParserBuilder toBuilder() {
        return builder()
                .withEncodedString(encodedString)
                .withFields(fields);
    }

    @Override
    public int getParseableCharacters() {
        return fields.split(encodedString).values().stream().mapToInt(String::length).sum();
    }

    public static class DefaultParserBuilder {

        private String encodedString;
        private Fields fields;

        public DefaultParserBuilder withEncodedString(String encodedString) {
            this.encodedString = encodedString;
            return this;
        }

        public DefaultParserBuilder withFields(Fields fields) {
            this.fields = fields;
            return this;
        }

        public DefaultParser build() {
            if (isNull(encodedString)) {
                this.encodedString = "";
            }

            return new DefaultParser(encodedString, fields);
        }

    }

}
