package cz.muni.fi.fits.gui.models.inputdata;

import cz.muni.fi.fits.gui.utils.Constants;

/**
 * Class for storing input data for operation <code>Change value of existing record</code>
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class ChangeValueByKeywordInputData extends InputDataBase {

    private final String _keyword;
    private final String _value;
    private final String _comment;
    private final boolean _addNewIfNotExist;

    /**
     * TODO
     * @param keyword
     * @param value
     * @param comment
     * @param addNewIfNotExist
     */
    public ChangeValueByKeywordInputData(String keyword, String value, String comment, boolean addNewIfNotExist) {
        _keyword = keyword;
        _value = value;
        _comment = comment;
        _addNewIfNotExist = addNewIfNotExist;

        _operation = Operation.CHANGE_VALUE;
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

        return _operation.getStringValue() + Constants.EXPRESSIONS_DELIMITER +
                ((_addNewIfNotExist) ? "-a" + Constants.EXPRESSIONS_DELIMITER : "") +
                _inputFilePath + Constants.EXPRESSIONS_DELIMITER +
                _keyword.toUpperCase() + Constants.EXPRESSIONS_DELIMITER +
                _value + Constants.EXPRESSIONS_DELIMITER +
                ((_comment != null) ? _comment : "");
    }
}
