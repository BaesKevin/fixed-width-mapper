package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import java.util.ArrayList;
import java.util.List;

public class FlatObjectMetadata {

    private final List<FieldMetadata<?>> fields;

    private FlatObjectMetadata(List<FieldMetadata<?>> fields) {
        this.fields = fields;
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

    public static FlatFixedWidthObjectBuilder builder() {
        return new FlatFixedWidthObjectBuilder();
    }

    public static class FlatFixedWidthObjectBuilder {
        private List<FieldMetadata<?>> fields;

        public FlatFixedWidthObjectBuilder() {
            this.fields = new ArrayList<>();
        }

        public FlatFixedWidthObjectBuilder add(FieldMetadata<?> field) {
            fields.add(field);
            return this;
        }

        public FlatObjectMetadata build() {
            return new FlatObjectMetadata(fields);
        }
    }
}
