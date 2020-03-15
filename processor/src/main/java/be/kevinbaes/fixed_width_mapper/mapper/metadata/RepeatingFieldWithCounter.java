package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import java.util.List;
import java.util.stream.Collectors;

public class RepeatingFieldWithCounter<U> implements Field<List<U>> {

    private final String name;
    private final Field<U> template;
    private final Field<Integer> counter;

    public RepeatingFieldWithCounter(String name, Field<Integer> counter, Field<U> template) {
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
        return new RepeatingFieldWithCounter<>(name, counter, template);
    }

    @Override
    public ParseResult<List<U>> parseWithResult(String encodedString) {
        ParseResult<Integer> counterParseResult = counter.parseWithResult(encodedString);
        int counterWidth = counterParseResult.getCharsRead();
        int numOfFields = counterParseResult.getValue();

        return getListParseResult(encodedString, counterWidth, numOfFields);
    }

    private ParseResult<List<U>> getListParseResult(String encodedString, int counterWidth, int numOfFields) {
        Field<List<U>> repeatingField = new RepeatingField<>(name + "fields", numOfFields, template);

        ParseResult<List<U>> listParseResult = repeatingField.parseWithResult(encodedString.substring(counterWidth));

        return new ParseResult<>(listParseResult.getValue(), listParseResult.getCharsRead() + counterWidth);
    }

    @Override
    public String toFullWidthString(List<U> field) {
        String counter = this.counter.toFullWidthString(field.size());
        String fields = field.stream().map(template::toFullWidthString).collect(Collectors.joining(""));

        return counter + fields;
    }

}
