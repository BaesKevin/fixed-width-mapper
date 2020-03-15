package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import be.kevinbaes.fixed_width_mapper.mapper.CachedParser;
import be.kevinbaes.fixed_width_mapper.mapper.Parser;

import java.util.List;

public abstract class CompositeField <T> implements Field<T> {

    private String name;
    private final CachedParser.CachedParserBuilder parserBuilder;

    public CompositeField(String name, List<Field<?>> fields) {
        this.name = name;
        parserBuilder = CachedParser.builder().withFields(fields.toArray(new Field[0]));
    }

    @Override
    public String getName() {
        return name;
    }

    protected Parser getParser(String s) {
        return parserBuilder.withEncodedString(s).build();
    }

    public abstract Field<T> setName(String name);
    public ParseResult<T> parseWithResult(String s) {
        return parseForParser(getParser(s));
    };

    public abstract ParseResult<T> parseForParser(Parser parser);
    public abstract String toFullWidthString(T field);
}
