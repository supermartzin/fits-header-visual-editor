package cz.muni.fi.fits.exceptions;

/**
 *
 * TODO description
 */
public class FitsHeaderException extends FitsFileException {
    public FitsHeaderException(String fileName) {
        super(fileName);
    }

    public FitsHeaderException(String fileName, String message) {
        super(fileName, message);
    }

    public FitsHeaderException(String fileName, String message, Throwable cause) {
        super(fileName, message, cause);
    }

    public FitsHeaderException(String fileName, Throwable cause) {
        super(fileName, cause);
    }
}
