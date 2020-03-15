package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RepeatingField<U> implements Field<List<U>> {

    private final String name;
    private final Field<U> template;
    private final Field<Integer> counter;

    public RepeatingField(String name, Field<Integer> counter, Field<U> template) {
        this.name = name;
        this.counter = counter;
        this.template = template;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Field<List<U>> setName(String name) {
        return new RepeatingField<>(name, counter, template);
    }

    @Override
    public ParseResult<List<U>> parseWithResult(String s) {
        ParseResult<Integer> integerParseResult = counter.parseWithResult(s);
        int counterWidth = integerParseResult.getCharsRead();
        int count = integerParseResult.getValue();

        Fields repeatedFields = createRepeatedFields(count);
        List<U> result = new ArrayList<>();
        int charsRead = counterWidth;

        for (Field<?> field : repeatedFields.fields()) {
            String current = s.substring(charsRead);
            ParseResult<U> result1 = (ParseResult<U>) field.parseWithResult(current);
            charsRead += result1.getCharsRead();
            result.add(result1.getValue());
        }

        return new ParseResult<>(result, charsRead);
    }

    private Fields createRepeatedFields(int count) {
        Fields.FieldsBuilder repeatedFieldsBuilder = Fields.builder();
        IntStream.range(0, count).forEach(i -> repeatedFieldsBuilder.addField(template.setName("base" + i)));

        return repeatedFieldsBuilder.build();
    }

    @Override
    public String toFullWidthString(List<U> field) {
        String counter = this.counter.toFullWidthString(field.size());
        String fields = field.stream().map(template::toFullWidthString).collect(Collectors.joining(""));

        return counter + fields;
    }

}
