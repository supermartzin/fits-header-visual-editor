package cz.muni.fi.fits.exceptions;

/**
 * Exception class used when input data are
 * either not in correct format or contains illegal data
 * or there is some
 *
 * @author Martin Vrábel
 * @version 1.0
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
