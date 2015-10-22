package cz.muni.fi.fits.gui.models.inputdata;

import cz.muni.fi.fits.gui.utils.Constants;

/**
 * Class for storing input data for operation <code>Add new record to end</code>
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class AddNewRecordInputData extends InputDataBase {

    /**
     * Type of {@link Operation} for which this class stores input data
     */
    public static final Operation OPERATION = Operation.ADD_TO_END;

    private final String _keyword;
    private final String _value;
    private final String _comment;
    private final boolean _updateIfExists;

    public AddNewRecordInputData(String keyword, String value, String comment, boolean updateIfExists) {
        _keyword = keyword;
        _value = value;
        _comment = comment;
        _updateIfExists = updateIfExists;
    }

    /**
     * {@inheritDoc}
     *
     * @return  {@link String} with ordered and formatted input data
     *          or <code>null</code> if some of the required parameters is not set
     */
    @Override
    public String getInputDataString() {
        if (_keyword == null
                || _value == null
                || _inputFilePath == null)
            return null;

        return OPERATION.getStringValue() + Constants.EXPRESSIONS_DELIMITER +
                ((_updateIfExists) ? "-u" + Constants.EXPRESSIONS_DELIMITER : "") +
                _inputFilePath + Constants.EXPRESSIONS_DELIMITER +
                _keyword.toUpperCase() + Constants.EXPRESSIONS_DELIMITER +
                _value + Constants.EXPRESSIONS_DELIMITER +
                ((_comment != null) ? _comment : "");
    }
}
