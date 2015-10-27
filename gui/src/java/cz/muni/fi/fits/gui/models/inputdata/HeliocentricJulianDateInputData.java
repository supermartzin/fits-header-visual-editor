package cz.muni.fi.fits.gui.models.inputdata;

import cz.muni.fi.fits.gui.utils.Constants;
import cz.muni.fi.fits.gui.utils.StringUtils;

/**
 * Class for storing input data for operation <code>Compute Heliocentric Julian Date</code>
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class HeliocentricJulianDateInputData extends InputDataBase {

    private final String _datetime;
    private final String _exposure;
    private final String _rightAscension;
    private final String _declination;
    private final String _comment;

    /**
     * TODO
     * @param datetime
     * @param exposure
     * @param rightAscension
     * @param declination
     * @param comment
     */
    public HeliocentricJulianDateInputData(String datetime, String exposure, String rightAscension, String declination, String comment) {
        _datetime = datetime;
        _exposure = exposure;
        _rightAscension = rightAscension;
        _declination = declination;
        _comment = comment;

        _operation = Operation.HJD;
    }

    /**
     * {@inheritDoc}
     *
     * @return  {@link String} with ordered and formatted input data
     *          or <code>null</code> if some of the required parameters is not set
     */
    @Override
    public String getInputDataString() {
        if (_datetime == null
                || _exposure == null
                || _rightAscension == null
                || _declination == null
                || _inputFilePath == null)
            return null;

        // handle whitespaces
        String comment = StringUtils.wrapIfContainsWhitespace(_comment, "\"");

        return _operation.getStringValue() + Constants.EXPRESSIONS_DELIMITER +
                _inputFilePath + Constants.EXPRESSIONS_DELIMITER +
                _datetime + Constants.EXPRESSIONS_DELIMITER +
                _exposure + Constants.EXPRESSIONS_DELIMITER +
                _rightAscension + Constants.EXPRESSIONS_DELIMITER +
                _declination + Constants.EXPRESSIONS_DELIMITER +
                ((comment != null) ? comment : "");
    }
}
