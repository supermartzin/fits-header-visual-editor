package cz.muni.fi.fits.gui.models.inputdata;

import cz.muni.fi.fits.gui.utils.Constants;

/**
 * Class for storing input data for operation <code>Compute Julian Date</code>
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class JulianDateInputData extends InputDataBase {

    private final String _datetime;
    private final String _exposure;
    private final String _comment;

    /**
     * TODO
     * @param datetime
     * @param exposure
     * @param comment
     */
    public JulianDateInputData(String datetime, String exposure, String comment) {
        _datetime = datetime;
        _exposure = exposure;
        _comment = comment;

        _operation = Operation.JD;
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
                || _inputFilePath == null)
            return null;

        return _operation.getStringValue() + Constants.EXPRESSIONS_DELIMITER +
                _inputFilePath + Constants.EXPRESSIONS_DELIMITER +
                _datetime + Constants.EXPRESSIONS_DELIMITER +
                _exposure + Constants.EXPRESSIONS_DELIMITER +
                ((_comment != null) ? _comment : "");
    }
}
