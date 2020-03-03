package be.kevinbaes.fixed_width_mapper.mapper;

import java.util.ArrayList;
import java.util.List;

public class FlatFixedWidthObjectMetadata {

    private final List<FixedWidthFieldMetadata> fields;

    private FlatFixedWidthObjectMetadata(List<FixedWidthFieldMetadata> fields) {
        this.fields = fields;
    }

    public int getStartingPosition(FixedWidthFieldMetadata fieldToRead) {
        int startingPosition = 0;

        for (FixedWidthFieldMetadata field : fields) {
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
        private List<FixedWidthFieldMetadata> fields;

        public FlatFixedWidthObjectBuilder() {
            this.fields = new ArrayList<>();
        }

        public FlatFixedWidthObjectBuilder add(FixedWidthFieldMetadata field) {
            fields.add(field);
            return this;
        }

        public FlatFixedWidthObjectMetadata build() {
            return new FlatFixedWidthObjectMetadata(fields);
        }
    }
}
