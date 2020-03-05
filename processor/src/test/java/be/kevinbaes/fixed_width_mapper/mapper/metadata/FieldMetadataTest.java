package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FieldMetadataTest {

    @Test
    public void isEqualTo_trueWhenNameSame() {
        StringFieldMetadata field1 = new StringFieldMetadata("field1", 5);
        StringFieldMetadata equalField = new StringFieldMetadata("field1", 2);
        assertTrue(field1.isEqualTo(equalField));
    }

}