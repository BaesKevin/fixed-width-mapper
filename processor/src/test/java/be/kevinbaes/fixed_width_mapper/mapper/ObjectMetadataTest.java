package be.kevinbaes.fixed_width_mapper.mapper;

import be.kevinbaes.fixed_width_mapper.mapper.metadata.FieldMetadata;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.ObjectMetadata;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.StringFieldMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ObjectMetadataTest {

    private ObjectMetadata twoStringFields;
    private FieldMetadata<String> field1;
    private FieldMetadata<String> field2;

    @BeforeEach
    void setUp() {
        field1 = new StringFieldMetadata("field1",5);
        field2 = new StringFieldMetadata("field2",10);

        twoStringFields = ObjectMetadata.builder()
                .addField(field1)
                .addField(field2)
                .build();
    }

    @Test
    public void objectMetadataThrowsOnDuplicateName() {
        StringFieldMetadata duplicate = new StringFieldMetadata("field1", 10);

        ObjectMetadata.FlatFixedWidthObjectBuilder builder = ObjectMetadata.builder()
                .addField(field1);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> builder.addField(duplicate));
    }

    @Test
    public void startingPositionTest() {
        assertThat(twoStringFields.getStartingPosition(field1)).isEqualTo(0);
        assertThat(twoStringFields.getStartingPosition(field2)).isEqualTo(5);
    }

    @Test
    public void addObjectAddsAllnestedFields() {
        FieldMetadata<String> stringField = new StringFieldMetadata("field1",7);
        StringFieldMetadata nestedField = new StringFieldMetadata("field2", 4);
        ObjectMetadata nestedObjectMetadata = ObjectMetadata.builder()
                .addField(nestedField)
                .build();

        ObjectMetadata parent = ObjectMetadata.builder()
                .addField(stringField)
                .addObject(nestedObjectMetadata)
                .build();

        assertThat(parent.getField("field1")).isEqualTo(stringField);
        assertThat(parent.getField("field2")).isEqualTo(nestedField);
    }

    @Test
    public void getStartingPositionWorksForNestedFields() {
        FieldMetadata<String> stringField = new StringFieldMetadata("field1",7);
        StringFieldMetadata nestedField = new StringFieldMetadata("field2", 4);
        ObjectMetadata nestedObjectMetadata = ObjectMetadata.builder()
                .addField(nestedField)
                .build();

        ObjectMetadata parent = ObjectMetadata.builder()
                .addField(stringField)
                .addObject(nestedObjectMetadata)
                .build();

        assertThat(parent.getStartingPosition(stringField)).isEqualTo(0);
        assertThat(parent.getStartingPosition(nestedField)).isEqualTo(7);
    }

}