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
        Field<?> field = getField(fieldName);
        int fieldStart = getStartingPosition(field);
        int fieldEnd = fieldStart + field.getWidth();

        return encoded.substring(fieldStart, fieldEnd);
    }

    public Field<?> getField(String name) {
        return fields.stream()
                .filter(field -> field.getName().equals(name))
                .findFirst()
                .orElseThrow();
    }

    int getStartingPosition(Field<?> fieldToRead) {
        int startingPosition = 0;

        for (Field<?> field : fields) {
            if (field.isEqualTo(fieldToRead)) {
                break;
            }
            startingPosition += field.getWidth();
        }

        return startingPosition;
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
