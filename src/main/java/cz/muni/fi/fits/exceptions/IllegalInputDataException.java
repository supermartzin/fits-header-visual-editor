package cz.muni.fi.fits.exceptions;

/**
 *
 * TODO description
 */
public class IllegalInputDataException extends Exception {
    public IllegalInputDataException() {
        super();
    }

    public IllegalInputDataException(String message) {
        super(message);
    }

    public IllegalInputDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalInputDataException(Throwable cause) {
        super(cause);
    }
}
