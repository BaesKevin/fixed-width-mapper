package be.kevinbaes.fixed_width_mapper.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FlatFixedWidthObjectMetadataTest {

    private FlatFixedWidthObjectMetadata twoStringFields;
    private FixedWidthFieldMetadata field1;
    private FixedWidthFieldMetadata field2;

    @BeforeEach
    void setUp() {
        field1 = new FixedWidthStringFieldMetadata("field1",5);
        field2 = new FixedWidthStringFieldMetadata("field2",10);

        twoStringFields = FlatFixedWidthObjectMetadata.builder()
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