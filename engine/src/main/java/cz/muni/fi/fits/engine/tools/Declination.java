package cz.muni.fi.fits.engine.tools;

/**
 * Class for computing decliantion of some object from provided coordinates
 *
 * @author Martin Vrábel
 * @version 1.1
 */
public class Declination {

    private final double _degrees;
    private final double _minutes;
    private final double _seconds;

    /**
     * Creates new {@link Declination} object with provided object's coordinates
     *
     * @param degrees   number of object's degrees
     * @param minutes   number of object's minutes
     * @param seconds   number of object's seconds
     */
    public Declination(double degrees, double minutes, double seconds) {
        _degrees = degrees;
        _minutes = minutes;
        _seconds = seconds;
    }

    /**
     * Computes object's declination from provided object's coordinates
     *
     * @return  computed object's declination
     */
    public double getDeclination() {
        return Double.compare(_degrees, 0) < 0
                ? -1 * (Math.abs(_degrees) + _minutes / 60.0 + _seconds / 3600.0)
                : Math.abs(_degrees) + _minutes / 60.0 + _seconds / 3600.0;
    }

    public double getDegrees() {
        return _degrees;
    }

    public double getMinutes() {
        return _minutes;
    }

    public double getSeconds() {
        return _seconds;
    }
}
