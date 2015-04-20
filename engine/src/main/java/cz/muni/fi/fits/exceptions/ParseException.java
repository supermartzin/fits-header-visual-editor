package cz.muni.fi.fits.exceptions;

/**
 * Exception class used in cases when
 * some specific value type cannot be parsed
 * from another value
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class ParseException extends RuntimeException {
    public ParseException() {
        super();
    }

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }
}
