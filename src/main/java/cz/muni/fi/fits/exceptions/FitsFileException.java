package cz.muni.fi.fits.exceptions;

/**
 *
 * TODO description
 */
public class FitsFileException extends EditingEngineException {

    private final String _fileName;

    public FitsFileException(String fileName) {
        super();
        this._fileName = fileName;
    }

    public FitsFileException(String fileName, String message) {
        super(message);
        this._fileName = fileName;
    }

    public FitsFileException(String fileName, String message, Throwable cause) {
        super(message, cause);
        this._fileName = fileName;
    }

    public FitsFileException(String fileName, Throwable cause) {
        super(cause);
        this._fileName = fileName;
    }

    public String getFileName() {
        return _fileName;
    }
}
