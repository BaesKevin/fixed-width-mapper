package be.kevinbaes.fixed_width_mapper.mapper;

import be.kevinbaes.fixed_width_mapper.mapper.metadata.FieldMetadata;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.FlatObjectMetadata;
import be.kevinbaes.fixed_width_mapper.mapper.metadata.StringFieldMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FlatObjectMetadataTest {

    private FlatObjectMetadata twoStringFields;
    private FieldMetadata field1;
    private FieldMetadata field2;

    @BeforeEach
    void setUp() {
        field1 = new StringFieldMetadata("field1",5);
        field2 = new StringFieldMetadata("field2",10);

        twoStringFields = FlatObjectMetadata.builder()
                .add(field1)
                .add(field2)
                .build();
    }

    @Test
    public void startingPositionTest() {
        String encoded = String.format("%5s%10s", "foo", "bar");
        assertThat(twoStringFields.getStartingPosition(field1)).isEqualTo(0);
        assertThat(twoStringFields.getStartingPosition(field2)).isEqualTo(5);
    }


}