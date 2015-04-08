package cz.muni.fi.fits.exceptions;

/**
 *
 * TODO description
 */
public class FitsFileException extends EditingEngineException {

    public FitsFileException() {
        super();
    }

    public FitsFileException(String message) {
        super(message);
    }

    public FitsFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public FitsFileException(Throwable cause) {
        super(cause);
    }
}
