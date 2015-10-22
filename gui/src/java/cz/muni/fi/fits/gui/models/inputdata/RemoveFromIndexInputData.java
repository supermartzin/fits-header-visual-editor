package cz.muni.fi.fits.gui.models.inputdata;

import cz.muni.fi.fits.gui.utils.Constants;

/**
 * Class for storing input data for operation <code>Remove record from index</code>
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class RemoveFromIndexInputData extends InputDataBase {

    /**
     * Type of {@link Operation} for which this class stores input data
     */
    public static final Operation OPERATION = Operation.REMOVE_FROM_INDEX;

    private final int _index;

    public RemoveFromIndexInputData(int index) {
        _index = index;
    }

    /**
     * {@inheritDoc}
     *
     * @return  {@link String} with ordered and formatted input data
     *          or <code>null</code> if some of the required parameters is not set
     */
    @Override
    public String getInputDataString() {
        if (_inputFilePath == null
                || _index < 1)
            return null;

        return OPERATION.getStringValue() + Constants.EXPRESSIONS_DELIMITER +
                _inputFilePath + Constants.EXPRESSIONS_DELIMITER +
                Integer.toString(_index, 10);
    }
}
