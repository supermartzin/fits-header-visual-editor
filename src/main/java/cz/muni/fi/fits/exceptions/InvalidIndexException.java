package cz.muni.fi.fits.exceptions;

/**
 *
 * TODO description
 */
public class InvalidIndexException extends IllegalInputDataException {

    private int _index;

    public int getIndex() {
        return _index;
    }

    public InvalidIndexException(int index) {
        super();
        this._index = index;
    }

    public InvalidIndexException(int index, String message) {
        super(message);
        this._index = index;
    }

    public InvalidIndexException(int index, String message, Throwable cause) {
        super(message, cause);
        this._index = index;
    }

    public InvalidIndexException(int index, Throwable cause) {
        super(cause);
        this._index = index;
    }
}
