package cz.muni.fi.fits.gui.models.inputdata;

import cz.muni.fi.fits.gui.utils.Constants;

/**
 * Class for storing input data for operation <code>Add new record to index</code>
 *
 * @author Martin Vrábel - © 2015 FITS-HeaderVisualEditor
 * @version 1.0
 */
public class AddNewToIndexInputData extends InputDataBase {

    private final String _keyword;
    private final String _value;
    private final String _comment;
    private final int _index;
    private final boolean _removeOldIfExists;

    /**
     * TODO
     * @param keyword
     * @param value
     * @param comment
     * @param index
     * @param removeOldIfExists
     */
    public AddNewToIndexInputData(String keyword, String value, String comment, int index, boolean removeOldIfExists) {
        _keyword = keyword;
        _value = value;
        _comment = comment;
        _index = index;
        _removeOldIfExists = removeOldIfExists;

        _operation = Operation.ADD_TO_INDEX;
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
                || _index < 1
                || _inputFilePath == null)
            return null;

        return _operation.getStringValue() + Constants.EXPRESSIONS_DELIMITER +
                ((_removeOldIfExists) ? "-rm" + Constants.EXPRESSIONS_DELIMITER : "") +
                _inputFilePath + Constants.EXPRESSIONS_DELIMITER +
                Integer.toString(_index, 10) + Constants.EXPRESSIONS_DELIMITER +
                _keyword.toUpperCase() + Constants.EXPRESSIONS_DELIMITER +
                _value + Constants.EXPRESSIONS_DELIMITER +
                ((_comment != null) ? _comment : "");
    }
}
