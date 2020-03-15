package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class LocalDateField implements Field<LocalDate> {

    private final DateTimeFormatter format;
    private final String originalFormat;
    private String name;
    private int width;

    public LocalDateField(String name, String format) {
        this.name = name;
        this.originalFormat = format;
        this.format = DateTimeFormatter.ofPattern(format);
        this.width = format.length();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Field<LocalDate> setName(String name) {
        return new LocalDateField(name, originalFormat);
    }

    @Override
    public ParseResult<LocalDate> parseWithResult(String s) {
        TemporalAccessor temporal = format.parse(s.substring(0, width));
        LocalDate parse = LocalDate.from(temporal);
        return new ParseResult<>(parse, width);
    }

    @Override
    public String toFullWidthString(LocalDate field) {
        return format.format(field);
    }
}
