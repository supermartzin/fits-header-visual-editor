package cz.muni.fi.fits.gui.models.inputdata;

import cz.muni.fi.fits.gui.models.operationenums.ChainValueType;
import cz.muni.fi.fits.gui.utils.Constants;
import cz.muni.fi.fits.gui.utils.StringUtils;

import java.util.List;

/**
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class ChainRecordsInputData extends InputDataBase {

    private final String _keyword;
    private final boolean _updateIfExists;
    private final boolean _longstringsSupport;
    private final List<ChainTuple> _valuesToChain;
    private final String _comment;

    public ChainRecordsInputData(String keyword, boolean updateIfExists, boolean longstringsSupport,
                                 List<ChainTuple> valuesToChain, String comment) {
        _keyword = keyword;
        _updateIfExists = updateIfExists;
        _longstringsSupport = longstringsSupport;
        _valuesToChain = valuesToChain;
        _comment = comment;

        _operation = Operation.CHAIN_RECORDS;
    }

    @Override
    public String getInputDataString() {
        if (_keyword == null
                || _valuesToChain == null
                || _valuesToChain.isEmpty()
                || _inputFilePath == null)
            return null;

        String chainString = "";
        for (ChainTuple chainTuple : _valuesToChain) {

            // construc value
            String value = "";
            switch (chainTuple.getValueType()) {
                case KEYWORD:
                    value += "-k=";
                    break;
                case CONSTANT:
                    value += "-c=";
                    break;
            }
            value += chainTuple.getValue();

            // handle whitespaces
            chainString += Constants.EXPRESSIONS_DELIMITER + StringUtils.wrapIfContainsWhitespace(value, "\"");
        }

        // handle whitespaces in comment
        String comment = StringUtils.wrapIfContainsWhitespace(_comment, "\"");

        return _operation.getStringValue() + Constants.EXPRESSIONS_DELIMITER +
                ((_updateIfExists) ? "-u" + Constants.EXPRESSIONS_DELIMITER : "") +
                ((_longstringsSupport) ? "-l" + Constants.EXPRESSIONS_DELIMITER : "") +
                _inputFilePath + Constants.EXPRESSIONS_DELIMITER +
                _keyword.toUpperCase() +
                chainString + Constants.EXPRESSIONS_DELIMITER +
                ((comment != null) ? comment : "");
    }


    /**
     * TODO insert description
     *
     * @author Martin Vrábel
     * @version 1.0
     */
    public static class ChainTuple {

        private final ChainValueType _valueType;
        private final String _value;

        public ChainTuple(final ChainValueType valueType, final String value) {
            _valueType = valueType;
            _value = value;
        }

        public ChainValueType getValueType() {
            return _valueType;
        }

        public String getValue() {
            return _value;
        }
    }
}
