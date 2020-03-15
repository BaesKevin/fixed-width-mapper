package be.kevinbaes.fixed_width_mapper.mapper;

import be.kevinbaes.fixed_width_mapper.mapper.metadata.Field;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.Fields;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.ParsingException;

import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

public class CachedParser implements Parser {

    private String encodedString;
    private Fields fields;
    private Map<String, String> parsedFields;
    private String debugString;

    private CachedParser(String encodedString, Fields fields, Map<String, String> parsedFields, String debugString) {
        this.encodedString = requireNonNull(encodedString);
        this.fields = fields;
        this.parsedFields = parsedFields;
        this.debugString = debugString;
    }

    public static CachedParserBuilder builder() {
        return new CachedParserBuilder();
    }

    public CachedParserBuilder toBuilder() {
        return builder()
                .withEncodedString(encodedString)
                .withFields(fields);
    }

    @Override
    public <T> T getValueFor(Field<T> field) {
        String fieldAsText = parsedFields.get(field.getName());
        return field.parse(fieldAsText);
    }

    @Override
    public int getParseableCharacters() {
        return parsedFields.values().stream().mapToInt(String::length).sum();
    }

    @Override
    public String toString() {
        return debugString;
    }

    public CachedParser withEncodedString(String encodedString) {
        return toBuilder().withEncodedString(encodedString).build();
    }

    public static class CachedParserBuilder {

        private final Fields.FieldsBuilder fieldsBuilder;
        private String encodedString;

        public CachedParserBuilder() {
            fieldsBuilder = Fields.builder();
        }

        public CachedParserBuilder withEncodedString(String encodedString) {
            this.encodedString = encodedString;
            return this;
        }

        public CachedParserBuilder withFields(Field<?>... fields) {
            for (Field<?> field : fields) {
                fieldsBuilder.addField(field);
            }
            return this;
        }

        public CachedParserBuilder withFields(Fields fields) {
            fields.fields().forEach(this::withFields);
            return this;
        }

        private String debugString(Map<String, String> split) {
            String parsedParts = split.entrySet().stream()
                    .map(this::getDebugStringPart)
                    .collect(Collectors.joining("\n"));
            return format("encoded string:\n[%s]\n%s", encodedString, parsedParts);
        }

        private String getDebugStringPart(Map.Entry<String, String> entry) {
            String fieldname = entry.getKey();
            String fieldvalue = entry.getValue();

            return format("%s = [%s] (%d chars)", fieldname, fieldvalue, fieldvalue.length());
        }

        public CachedParser build() {
            if (isNull(encodedString)) {
                this.encodedString = "";
            }

            Fields fields = fieldsBuilder.build();
            Map<String, String> split = fields.split(encodedString);
            int parseableChars = split.values().stream().mapToInt(String::length).sum();
            String debugString = debugString(split);

            if(encodedString.length() < parseableChars) {
                throw new ParsingException("Encoded string contains less characters than can be parsed: \n" + debugString);
            }

            return new CachedParser(encodedString, fields, split, debugString);
        }

    }

}
