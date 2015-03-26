package cz.muni.fi.fits.exceptions;

/**
 *
 * TODO description
 */
public class WrongNumberOfParametersException extends IllegalInputDataException {

    private final int _numberOfParams;

    public int getNumberOfParameters() {
        return _numberOfParams;
    }

    public WrongNumberOfParametersException(int numberOfParams) {
        super();
        this._numberOfParams = numberOfParams;
    }

    public WrongNumberOfParametersException(int numberOfParams, String message) {
        super(message);
        this._numberOfParams = numberOfParams;
    }

    public WrongNumberOfParametersException(int numberOfParams, String message, Throwable cause) {
        super(message, cause);
        this._numberOfParams = numberOfParams;
    }

    public WrongNumberOfParametersException(int numberOfParams, Throwable cause) {
        super(cause);
        this._numberOfParams = numberOfParams;
    }
}
