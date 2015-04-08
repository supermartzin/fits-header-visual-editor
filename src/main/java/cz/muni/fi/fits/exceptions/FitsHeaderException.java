package cz.muni.fi.fits.exceptions;

/**
 *
 * TODO description
 */
public class FitsHeaderException extends EditingEngineException {
    public FitsHeaderException() {
        super();
    }

    public FitsHeaderException(String message) {
        super(message);
    }

    public FitsHeaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public FitsHeaderException(Throwable cause) {
        super(cause);
    }
}
