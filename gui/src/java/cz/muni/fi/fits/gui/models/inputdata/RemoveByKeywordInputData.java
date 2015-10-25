package cz.muni.fi.fits.gui.models.inputdata;

import cz.muni.fi.fits.gui.utils.Constants;

/**
 * Class for storing input data for operation <code>Remove record by keyword</code>
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class RemoveByKeywordInputData extends InputDataBase {

    private final String _keyword;

    /**
     * TODO
     * @param keyword
     */
    public RemoveByKeywordInputData(String keyword) {
        _keyword = keyword;

        _operation = Operation.REMOVE_BY_KEYWORD;
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
                || _inputFilePath == null)
            return null;

        return _operation.getStringValue() + Constants.EXPRESSIONS_DELIMITER +
                _inputFilePath + Constants.EXPRESSIONS_DELIMITER +
                _keyword.toUpperCase();
    }
}
