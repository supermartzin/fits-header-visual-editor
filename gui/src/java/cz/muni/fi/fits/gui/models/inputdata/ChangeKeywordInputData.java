package cz.muni.fi.fits.gui.models.inputdata;

import cz.muni.fi.fits.gui.utils.Constants;

/**
 * Class for storing input data for operation <code>Change keyword of existing record</code>
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class ChangeKeywordInputData extends InputDataBase {

    /**
     * Type of {@link Operation} for which this class stores input data
     */
    public static final Operation OPERATION = Operation.CHANGE_KEYWORD;

    private final String _oldKeyword;
    private final String _newKeyword;
    private final boolean _removeValueOfNewIfExists;

    public ChangeKeywordInputData(String oldKeyword, String newKeyword, boolean removeValueOfNewIfExists) {
        _oldKeyword = oldKeyword;
        _newKeyword = newKeyword;
        _removeValueOfNewIfExists = removeValueOfNewIfExists;
    }

    /**
     * {@inheritDoc}
     *
     * @return  {@link String} with ordered and formatted input data
     *          or <code>null</code> if some of the required parameters is not set
     */
    @Override
    public String getInputDataString() {
        if (_oldKeyword == null
                || _newKeyword == null
                || _inputFilePath == null)
            return null;

        return OPERATION.getStringValue() + Constants.EXPRESSIONS_DELIMITER +
                ((_removeValueOfNewIfExists) ? "-rm" + Constants.EXPRESSIONS_DELIMITER : "") +
                _inputFilePath + Constants.EXPRESSIONS_DELIMITER +
                _oldKeyword.toUpperCase() + Constants.EXPRESSIONS_DELIMITER +
                _newKeyword.toUpperCase();
    }
}
