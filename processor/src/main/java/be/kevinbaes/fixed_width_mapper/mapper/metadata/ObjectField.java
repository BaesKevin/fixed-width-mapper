package be.kevinbaes.fixed_width_mapper.mapper.metadata;

public class ObjectField implements Field<Fields> {

    private final String name;
    private final Fields fields;

    public ObjectField(String name, Fields fields) {
        this.name = name;
        this.fields = fields;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public Class<Fields> getTargetType() {
        return Fields.class;
    }

    @Override
    public Fields parse(String text) {
        return fields;
    }
}
