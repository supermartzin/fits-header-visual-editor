package cz.muni.fi.fits.models;

/**
 *
 * TODO description
 */
public class Result {

    private final boolean _success;
    private final String _message;

    public Result(boolean success, String message) {
        this._success = success;
        this._message = message;
    }

    public boolean isSuccess() {
        return _success;
    }

    public String getMessage() {
        return _message;
    }
}
