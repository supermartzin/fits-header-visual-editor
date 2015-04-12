package cz.muni.fi.fits.exceptions;

/**
 * Exception class used for case when operation
 * cannot be recognized from input data,
 * extends {@link IllegalInputDataException} class
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class UnknownOperationException extends IllegalInputDataException {

    private final String _operation;

    public String get_operation() {
        return _operation;
    }

    public UnknownOperationException(String operation) {
        super();
        this._operation = operation;
    }

    public UnknownOperationException(String operation, String message) {
        super(message);
        this._operation = operation;
    }

    public UnknownOperationException(String operation, String message, Throwable cause) {
        super(message, cause);
        this._operation = operation;
    }

    public UnknownOperationException(String operation, Throwable cause) {
        super(cause);
        this._operation = operation;
    }
}
