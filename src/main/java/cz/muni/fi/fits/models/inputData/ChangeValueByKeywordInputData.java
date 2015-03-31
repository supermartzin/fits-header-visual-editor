package cz.muni.fi.fits.models.inputData;

import cz.muni.fi.fits.models.OperationType;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * TODO description
 */
public class ChangeValueByKeywordInputData extends OneSwitchInputData {

    private final String _keyword;
    private final String _value;
    private final String _comment;

    public ChangeValueByKeywordInputData(String keyword, String value, String comment, boolean addNewIfNotExists) {
        this(keyword, value, comment, addNewIfNotExists, new HashSet<>());
    }

    public ChangeValueByKeywordInputData(String keyword, String value, String comment, boolean addNewIfNotExists, Collection<File> fitsFiles) {
        super(OperationType.CHANGE_KEYWORD, fitsFiles, addNewIfNotExists);
        this._keyword = keyword;
        this._value = value;
        this._comment = comment;
    }

    public String getKeyword() {
        return _keyword;
    }

    public String getValue() {
        return _value;
    }

    public String getComment() {
        return _comment;
    }

    public boolean addNewIfNotExists() {
        return _hasSwitch;
    }
}
