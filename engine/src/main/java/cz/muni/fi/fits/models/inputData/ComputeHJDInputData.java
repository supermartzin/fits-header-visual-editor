package cz.muni.fi.fits.models.inputData;

import cz.muni.fi.fits.models.DegreesObject;
import cz.muni.fi.fits.models.OperationType;
import cz.muni.fi.fits.models.TimeObject;
import cz.muni.fi.fits.utils.Constants;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

/**
 * Class encapsulating input data for operation <b>Compute Heliocentric Julian Date</b>
 *
 * @author Martin Vrábel
 * @version 1.0.1
 */
public class ComputeHJDInputData extends InputData {

    private final Object _datetime;
    private final Object _exposure;
    private final TimeObject _rightAscension;
    private final DegreesObject _declination;
    private final String _comment;

    /**
     * Creates new {@link ComputeHJDInputData} object with provided parameters
     *
     * @param datetime          keyword of record which contains DateTime value for HJD
     * @param exposure          keyword of record which contains Exposure value for HJD
     * @param rightAscension    right ascension of observed object
     * @param declination       declination of observed object
     * @param comment           comment of HJD value, insert <code>null</code>
     *                          for default value <b>"center of exposure"</b>
     */
    public ComputeHJDInputData(String datetime, String exposure,
                               TimeObject rightAscension, DegreesObject declination, String comment) {
        this(datetime, exposure, rightAscension, declination, comment, new HashSet<>());
    }

    /**
     * Creates new {@link ComputeHJDInputData} object with provided parameters
     *
     * @param datetime          keyword of record which contains DateTime value for HJD
     * @param exposure          value of Exposure in seconds
     * @param rightAscension    right ascension of observed object
     * @param declination       declination of observed object
     * @param comment           comment of HJD value, insert <code>null</code>
     *                          for default value <b>"center of exposure"</b>
     */
    public ComputeHJDInputData(String datetime, double exposure,
                               TimeObject rightAscension, DegreesObject declination, String comment) {
        this(datetime, exposure, rightAscension, declination, comment, new HashSet<>());
    }

    /**
     * Creates new {@link ComputeHJDInputData} object with provided parameters
     *
     * @param datetime          {@link LocalDateTime} value
     * @param exposure          keyword of record which contains Exposure value for HJD
     * @param rightAscension    right ascension of observed object
     * @param declination       declination of observed object
     * @param comment           comment of HJD value, insert <code>null</code>
     *                          for default value <b>"center of exposure"</b>
     */
    public ComputeHJDInputData(LocalDateTime datetime, String exposure,
                               TimeObject rightAscension, DegreesObject declination, String comment) {
        this(datetime, exposure, rightAscension, declination, comment, new HashSet<>());
    }

    /**
     * Creates new {@link ComputeHJDInputData} object with provided parameters
     *
     * @param datetime          {@link LocalDateTime} value
     * @param exposure          value of Exposure in seconds
     * @param rightAscension    right ascension of observed object
     * @param declination       declination of observed object
     * @param comment           comment of HJD value, insert <code>null</code>
     *                          for default value <b>"center of exposure"</b>
     */
    public ComputeHJDInputData(LocalDateTime datetime, double exposure,
                               TimeObject rightAscension, DegreesObject declination, String comment) {
        this(datetime, exposure, rightAscension, declination, comment, new HashSet<>());
    }

    /**
     * Creates new {@link ComputeHJDInputData} object with provided parameters
     *
     * @param datetime          keyword of record which contains DateTime value for HJD
     * @param exposure          keyword of record which contains Exposure value for HJD
     * @param rightAscension    right ascension of observed object
     * @param declination       declination of observed object
     * @param comment           comment of HJD value, insert <code>null</code>
     *                          for default value <b>"center of exposure"</b>
     * @param fitsFiles         FITS files in which to shift time of time record
     */
    public ComputeHJDInputData(String datetime, String exposure,
                               TimeObject rightAscension, DegreesObject declination,
                               String comment, Collection<File> fitsFiles) {
        super(OperationType.COMPUTE_HJD, fitsFiles);
        _datetime = datetime != null ? datetime.toUpperCase() : null;
        _exposure = exposure != null ? exposure.toUpperCase() : null;
        _rightAscension = rightAscension;
        _declination = declination;
        _comment = comment != null ? comment : Constants.DEFAULT_HJD_COMMENT;
    }

    /**
     * Creates new {@link ComputeHJDInputData} object with provided parameters
     *
     * @param datetime          keyword of record which contains DateTime value for HJD
     * @param exposure          value of Exposure in seconds
     * @param rightAscension    right ascension of observed object
     * @param declination       declination of observed object
     * @param comment           comment of HJD value, insert <code>null</code>
     *                          for default value <b>"center of exposure"</b>
     * @param fitsFiles         FITS files in which to shift time of time record
     */
    public ComputeHJDInputData(String datetime, double exposure,
                               TimeObject rightAscension, DegreesObject declination,
                               String comment, Collection<File> fitsFiles) {
        super(OperationType.COMPUTE_HJD, fitsFiles);
        _datetime = datetime != null ? datetime.toUpperCase() : null;
        _exposure = exposure;
        _rightAscension = rightAscension;
        _declination = declination;
        _comment = comment != null ? comment : Constants.DEFAULT_HJD_COMMENT;
    }

    /**
     * Creates new {@link ComputeHJDInputData} object with provided parameters
     *
     * @param datetime          {@link LocalDateTime} value
     * @param exposure          keyword of record which contains Exposure value for HJD
     * @param rightAscension    right ascension of observed object
     * @param declination       declination of observed object
     * @param comment           comment of HJD value, insert <code>null</code>
     *                          for default value <b>"center of exposure"</b>
     * @param fitsFiles         FITS files in which to shift time of time record
     */
    public ComputeHJDInputData(LocalDateTime datetime, String exposure,
                               TimeObject rightAscension, DegreesObject declination,
                               String comment, Collection<File> fitsFiles) {
        super(OperationType.COMPUTE_HJD, fitsFiles);
        _datetime = datetime;
        _exposure = exposure != null ? exposure.toUpperCase() : null;
        _rightAscension = rightAscension;
        _declination = declination;
        _comment = comment != null ? comment : Constants.DEFAULT_HJD_COMMENT;
    }

    /**
     * Creates new {@link ComputeHJDInputData} object with provided parameters
     *
     * @param datetime          {@link LocalDateTime} value
     * @param exposure          value of Exposure in seconds
     * @param rightAscension    right ascension of observed object
     * @param declination       declination of observed object
     * @param comment           comment of HJD value, insert <code>null</code>
     *                          for default value <b>"center of exposure"</b>
     * @param fitsFiles         FITS files in which to shift time of time record
     */
    public ComputeHJDInputData(LocalDateTime datetime, double exposure,
                               TimeObject rightAscension, DegreesObject declination,
                               String comment, Collection<File> fitsFiles) {
        super(OperationType.COMPUTE_HJD, fitsFiles);
        _datetime = datetime;
        _exposure = exposure;
        _rightAscension = rightAscension;
        _declination = declination;
        _comment = comment != null ? comment : Constants.DEFAULT_HJD_COMMENT;
    }

    public Object getDatetime() {
        return _datetime;
    }

    public Object getExposure() {
        return _exposure;
    }

    public TimeObject getRightAscension() {
        return _rightAscension;
    }

    public DegreesObject getDeclination() {
        return _declination;
    }

    public String getComment() {
        return _comment;
    }
}
