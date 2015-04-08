package cz.muni.fi.fits.models.inputData;

import cz.muni.fi.fits.models.OperationType;
import cz.muni.fi.fits.utils.Tuple;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

/**
 *
 * TODO description
 */
public class ChainRecordsInputData extends SwitchInputData {

    private final String _keyword;
    private final LinkedList<Tuple> _chainValues;
    private final String _comment;

    public ChainRecordsInputData(String keyword, LinkedList<Tuple> chainValues, String comment, boolean updateIfExists, boolean skipIfChainKwNotExists) {
        this(keyword, chainValues, comment, updateIfExists, skipIfChainKwNotExists, new HashSet<>());
    }

    public ChainRecordsInputData(String keyword, LinkedList<Tuple> chainValues, String comment, boolean updateIfExists, boolean skipIfChainKwNotExists, Collection<File> fitsFiles) {
        super(OperationType.CHAIN_RECORDS, fitsFiles);
        this._keyword = keyword != null ? keyword.toUpperCase() : null;
        this._chainValues = chainValues;
        this._comment = comment;
        this._switches.put("updateIfExists", updateIfExists);
        this._switches.put("skipIfChainKwNotExists", skipIfChainKwNotExists);
    }

    public String getKeyword() {
        return _keyword;
    }

    public LinkedList<Tuple> getChainValues() {
        return _chainValues;
    }

    public String getComment() {
        return _comment;
    }

    public boolean updateIfExists() {
        return _switches.get("updateIfExists");
    }

    public boolean skipIfChainKwNotExists() {
        return _switches.get("skipIfChainKwNotExists");
    }
}
