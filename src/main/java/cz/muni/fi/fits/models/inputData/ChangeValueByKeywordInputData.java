package cz.muni.fi.fits.models.inputData;

import cz.muni.fi.fits.models.OperationType;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * TODO description
 */
public class ChangeValueByKeywordInputData extends SwitchInputData {

    private final String _keyword;
    private final Object _value;
    private final String _comment;

    public ChangeValueByKeywordInputData(String keyword, Object value, String comment, boolean addNewIfNotExists) {
        this(keyword, value, comment, addNewIfNotExists, new HashSet<>());
    }

    public ChangeValueByKeywordInputData(String keyword, Object value, String comment, boolean addNewIfNotExists, Collection<File> fitsFiles) {
        super(OperationType.CHANGE_KEYWORD, fitsFiles);
        this._keyword = keyword;
        this._value = value;
        this._comment = comment;
        this._switches.put("addNewIfNotExists", addNewIfNotExists);
    }

    public String getKeyword() {
        return _keyword;
    }

    public Object getValue() {
        return _value;
    }

    public String getComment() {
        return _comment;
    }

    public boolean addNewIfNotExists() {
        return _switches.get("addNewIfNotExists");
    }
}
