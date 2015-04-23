package cz.muni.fi.fits.exceptions;

/**
 * Exception class used when error occurs during configuration phase
 * either with data or loading process
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class ConfigurationException extends Exception {
    public ConfigurationException() {
        super();
    }

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationException(Throwable cause) {
        super(cause);
    }
}
