package cz.muni.fi.fits.exceptions;

/**
 * Exception class used when input data are not in valid form
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class ValidationException extends Exception {

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}
