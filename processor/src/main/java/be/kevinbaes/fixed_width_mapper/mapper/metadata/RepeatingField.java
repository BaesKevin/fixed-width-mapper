package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import be.kevinbaes.fixed_width_mapper.mapper.DefaultParser;
import be.kevinbaes.fixed_width_mapper.mapper.Parser;

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

    // TODO find a way to get the width without having to parse the text
    @Override
    public int getWidth(String text) {
        Fields counterFields = Fields.builder().addField(counter).build();
        Parser counterParser = DefaultParser.builder().withEncodedString(text).withFields(counterFields).build();
        int count = counterParser.parseField(counter);
        return counter.getWidth(text) + template.getWidth(text) * count;
    }

    @Override
    public List<U> parse(String text) {
        int count = parseCount(text);
        List<?> result = parseRepeatedFields(text, count);

        return (List<U>) result;
    }

    private List<?> parseRepeatedFields(String text, int count) {
        Fields repeatedFields = createRepeatedFields(count);
        Parser repeatingFieldsParser = DefaultParser.builder()
                .withEncodedString(text)
                .withFields(repeatedFields)
                .build();

        return repeatedFields.fields().stream()
                .map(repeatingFieldsParser::parseField)
                .collect(Collectors.toList());
    }

    private Fields createRepeatedFields(int count) {
        Fields.FieldsBuilder repeatedFieldsBuilder = Fields.builder().addField(counter);
        IntStream.range(0, count).forEach(i -> repeatedFieldsBuilder.addField(template.setName("base" + i)));

        return repeatedFieldsBuilder.build();
    }

    private int parseCount(String text) {
        Fields.FieldsBuilder fiedsBuilder = Fields.builder().addField(counter);
        Fields counterFields = fiedsBuilder.build();
        Parser counterParser = DefaultParser.builder().withEncodedString(text).withFields(counterFields).build();
        return counterParser.parseField(counter);
    }

    @Override
    public String toString(List<U> field) {
        return null;
    }
}
