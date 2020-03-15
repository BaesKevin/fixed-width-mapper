package be.kevinbaes.fixed_width_mapper.mapper.metadata;

import static java.lang.String.format;

public class ParsingException extends RuntimeException {
    public ParsingException(String message) {
        super(message);
    }
    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
