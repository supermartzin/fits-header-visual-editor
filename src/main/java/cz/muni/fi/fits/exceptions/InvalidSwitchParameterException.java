package cz.muni.fi.fits.exceptions;

/**
 * Exception class used for cases when switch parameter
 * in input data is incorrect or is in incorrect format,
 * extends {@link IllegalInputDataException} class
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class InvalidSwitchParameterException extends IllegalInputDataException {
    public InvalidSwitchParameterException() {
        super();
    }

    public InvalidSwitchParameterException(String message) {
        super(message);
    }

    public InvalidSwitchParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSwitchParameterException(Throwable cause) {
        super(cause);
    }
}
