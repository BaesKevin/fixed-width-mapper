package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RepeatingField <U> implements Field<List<U>> {

    private final Field<U> template;
    private String name;
    private int count;
    private Fields fields;

    public RepeatingField(String name, int count, Field<U> template) {
        this.name = name;
        this.count = count;
        this.template = template;

        fields = createRepeatedFields(count);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Field<List<U>> setName(String name) {
        return new RepeatingField<>(name, count, template);
    }

    @Override
    public ParseResult<List<U>> parseWithResult(String encodedString) {
        List<U> parsedValues = new ArrayList<>();
        int charsRead = 0;

        for (Field<?> field : fields.fields()) {
            String current = encodedString.substring(charsRead);
            ParseResult<U> resultForField = (ParseResult<U>) field.parseWithResult(current);
            charsRead += resultForField.getCharsRead();
            parsedValues.add(resultForField.getValue());
        }

        return new ParseResult<>(parsedValues, charsRead);
    }

    private Fields createRepeatedFields(int count) {
        Fields.FieldsBuilder repeatedFieldsBuilder = Fields.builder();
        IntStream.range(0, count).forEach(i -> repeatedFieldsBuilder.addField(template.setName(template.getName() + i)));

        return repeatedFieldsBuilder.build();
    }

    @Override
    public String toFullWidthString(List<U> field) {
        return field.stream().map(template::toFullWidthString).collect(Collectors.joining(""));
    }
}
