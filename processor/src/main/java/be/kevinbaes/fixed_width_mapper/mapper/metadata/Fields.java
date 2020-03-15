package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import java.util.*;

import static java.lang.String.format;

public class Fields {

    private final List<Field<?>> fields;

    public Fields(List<Field<?>> fields) {
        this.fields = fields;
    }

    public List<Field<?>> fields() {
        return Collections.unmodifiableList(fields);
    }

    public static FieldsBuilder builder() {
        return new FieldsBuilder();
    }

    public String getFieldAsText(String encoded, String fieldName) {
        return this.split(encoded).get(fieldName);
    }

    public <T> T parse(String encoded, Field<T> field) {
        Map<String, String> split = split(encoded);

        return field.parse(split.get(field.getName()));
    }

    public Map<String, String> split(String encoded) {
        Map<String, String> split = new HashMap<>();

        int totalCharsRead = 0;
        for (Field<?> field : fields) {
            ParseResult<?> parseResult = field.parseWithResult(encoded.substring(totalCharsRead));
            int charsReadForField = parseResult.getCharsRead();
            split.put(field.getName(), encoded.substring(totalCharsRead, totalCharsRead + charsReadForField));
            totalCharsRead += charsReadForField;
        }

        return split;
    }

    public static class FieldsBuilder {
        private List<Field<?>> fields;

        public FieldsBuilder() {
            this.fields = new ArrayList<>();
        }

        public FieldsBuilder addField(Field<?> field) {
            Optional<Field<?>> existingFieldOpt = fields.stream()
                    .filter(existingField -> existingField.isEqualTo(field))
                    .findFirst();

            if (existingFieldOpt.isPresent()) {
                throw new IllegalArgumentException(format("field [%s] exists", field.getName()));
            }

            fields.add(field);
            return this;
        }

        public Fields build() {
            return new Fields(fields);
        }
    }
}
