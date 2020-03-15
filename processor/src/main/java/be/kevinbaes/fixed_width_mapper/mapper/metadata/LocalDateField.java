package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class LocalDateField extends FixedWidthField<LocalDate> {

    private final DateTimeFormatter dateFormat;
    private final String originalFormat;

    public LocalDateField(String name, String dateFormat) {
        super(name, dateFormat.length());
        this.originalFormat = dateFormat;
        this.dateFormat = DateTimeFormatter.ofPattern(dateFormat);
    }

    @Override
    public Field<LocalDate> setName(String name) {
        return new LocalDateField(name, originalFormat);
    }

    @Override
    public LocalDate parseParseablePart(String parseablePart) {
        TemporalAccessor temporal = dateFormat.parse(parseablePart);
        return LocalDate.from(temporal);
    }

    @Override
    public String toFullWidthString(LocalDate field) {
        return dateFormat.format(field);
    }
}
