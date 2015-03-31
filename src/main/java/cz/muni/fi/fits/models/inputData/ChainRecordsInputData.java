package cz.muni.fi.fits.models.inputData;

import cz.muni.fi.fits.models.OperationType;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * TODO description
 */
public class ChainRecordsInputData extends TwoSwitchesInputData {

    private final String _keyword;
    private final Map<String, String> _chainValues;
    private final String _comment;

    public ChainRecordsInputData(String keyword, Map<String, String> chainValues, String comment, boolean updateIfExists, boolean skipIfChainKwNotExists) {
        this(keyword, chainValues, comment, updateIfExists, skipIfChainKwNotExists, new HashSet<>());
    }

    public ChainRecordsInputData(String keyword, Map<String, String> chainValues, String comment, boolean updateIfExists, boolean skipIfChainKwNotExists, Collection<File> fitsFiles) {
        super(OperationType.CHAIN_RECORDS, fitsFiles, updateIfExists, skipIfChainKwNotExists);
        this._keyword = keyword;
        this._chainValues = chainValues;
        this._comment = comment;
    }

    public String getKeyword() {
        return _keyword;
    }

    public Map<String, String> getChainValues() {
        return _chainValues;
    }

    public String getComment() {
        return _comment;
    }
}
