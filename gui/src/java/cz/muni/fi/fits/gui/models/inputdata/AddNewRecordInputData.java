package cz.muni.fi.fits.gui.models.inputdata;

import cz.muni.fi.fits.gui.utils.Constants;
import cz.muni.fi.fits.gui.utils.StringUtils;

/**
 * Class for storing input data for operation <code>Add new record to end</code>
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class AddNewRecordInputData extends InputDataBase {

    private final String _keyword;
    private final String _value;
    private final String _comment;
    private final boolean _updateIfExists;

    /**
     * TODO
     * @param keyword
     * @param value
     * @param comment
     * @param updateIfExists
     */
    public AddNewRecordInputData(String keyword, String value, String comment, boolean updateIfExists) {
        _keyword = keyword;
        _value = value;
        _comment = comment;
        _updateIfExists = updateIfExists;

        _operation = Operation.ADD_TO_END;
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

        // handle whitespaces
        String value = StringUtils.wrapIfContainsWhitespace(_value, "\"");
        String comment = StringUtils.wrapIfContainsWhitespace(_comment, "\"");

        return _operation.getStringValue() + Constants.EXPRESSIONS_DELIMITER +
                ((_updateIfExists) ? "-u" + Constants.EXPRESSIONS_DELIMITER : "") +
                _inputFilePath + Constants.EXPRESSIONS_DELIMITER +
                _keyword.toUpperCase() + Constants.EXPRESSIONS_DELIMITER +
                value + Constants.EXPRESSIONS_DELIMITER +
                ((comment != null) ? comment : "");
    }
}
