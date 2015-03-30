package cz.muni.fi.fits.exceptions;

/**
 *
 * TODO description
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
