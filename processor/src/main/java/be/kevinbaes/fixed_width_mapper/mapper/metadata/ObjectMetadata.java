package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

public class ObjectMetadata {

    private final List<FieldMetadata<?>> fields;

    private ObjectMetadata(List<FieldMetadata<?>> fields) {
        this.fields = fields;
    }

    public List<FieldMetadata<?>> fields() {
        return Collections.unmodifiableList(fields);
    }

    public int getStartingPosition(FieldMetadata<?> fieldToRead) {
        int startingPosition = 0;

        for (FieldMetadata<?> field : fields) {
            if (field.getName().equals(fieldToRead.getName())) {
                break;
            }
            startingPosition += field.getWidth();
        }

        return startingPosition;
    }

    public FieldMetadata<?> getField(String name) {
        return fields.stream().filter(field -> field.getName().equals(name)).findFirst().orElseThrow();
    }

    public static FlatFixedWidthObjectBuilder builder() {
        return new FlatFixedWidthObjectBuilder();
    }

    public static class FlatFixedWidthObjectBuilder {
        private List<FieldMetadata<?>> fields;

        public FlatFixedWidthObjectBuilder() {
            this.fields = new ArrayList<>();
        }

        public FlatFixedWidthObjectBuilder addField(FieldMetadata<?> field) {
            Optional<FieldMetadata<?>> existingFieldOpt = fields.stream().filter(existingField -> existingField.isEqualTo(field))
                    .findFirst();

            if (existingFieldOpt.isPresent()) {
                throw new IllegalArgumentException(format("field [%s] exists", field.getName()));
            }

            fields.add(field);
            return this;
        }

        public ObjectMetadata build() {
            return new ObjectMetadata(fields);
        }

        public FlatFixedWidthObjectBuilder addObject(ObjectMetadata nestedObjectMetadata) {
            nestedObjectMetadata.fields().forEach(this::addField);
            return this;
        }
    }
}
