package cz.muni.fi.fits.gui.models.inputdata;

import cz.muni.fi.fits.gui.utils.Constants;

/**
 * Class for storing input data for operation <code>Remove record from index</code>
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class RemoveFromIndexInputData extends InputDataBase {

    private final int _index;

    /**
     * TODO
     * @param index
     */
    public RemoveFromIndexInputData(int index) {
        _index = index;

        _operation = Operation.REMOVE_FROM_INDEX;
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

        return _operation.getStringValue() + Constants.EXPRESSIONS_DELIMITER +
                _inputFilePath + Constants.EXPRESSIONS_DELIMITER +
                Integer.toString(_index, 10);
    }
}
