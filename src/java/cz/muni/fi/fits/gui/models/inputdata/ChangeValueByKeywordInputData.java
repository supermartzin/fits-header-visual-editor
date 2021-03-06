package cz.muni.fi.fits.gui.models.inputdata;

import cz.muni.fi.fits.gui.utils.StringUtils;

import java.util.LinkedList;
import java.util.List;

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
     * TODO insert description
     *
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
     * @return  list of {@link String} arguments in required order
     *          or <code>null</code> if some of the required parameters is not set
     */
    @Override
    public List<String> getInputDataArguments() {
        if (_keyword == null
                || _value == null
                || _inputFilePath == null)
            return null;

        // handle whitespaces
        String value = StringUtils.wrapIfContainsWhitespace(_value, "\"");
        String comment = StringUtils.wrapIfContainsWhitespace(_comment, "\"");

        // created ordered list
        List<String> inputDataArguments = new LinkedList<>();
        inputDataArguments.add(_operation.getStringValue());
        if (_addNewIfNotExist)
            inputDataArguments.add("-a");
        inputDataArguments.add(_inputFilePath);
        inputDataArguments.add(_keyword.toUpperCase());
        inputDataArguments.add(value);
        if (comment != null)
            inputDataArguments.add(comment);

        return inputDataArguments;
    }
}
