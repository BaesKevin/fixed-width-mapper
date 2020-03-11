package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectFieldTest {

    @Test
    public void objectMetadataTest() {
        Fields fields = Fields.builder()
                .addField(new IntegerField("field", 3))
                .addField(new IntegerField("field2", 5))
                .build();

        Field<Fields> objectMetadata = new ObjectField("object", fields);

        assertThat(objectMetadata.getName()).isEqualTo("object");
        assertThat(objectMetadata.getTargetType()).isEqualTo(Fields.class);
    }

}