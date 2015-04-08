package cz.muni.fi.fits.models.inputData;

import cz.muni.fi.fits.models.OperationType;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * TODO description
 */
public class AddNewRecordInputData extends SwitchInputData {

    private final String _keyword;
    private final Object _value;
    private final String _comment;

    public AddNewRecordInputData(String keyword, Object value, String comment, boolean updateIfExists) {
        this(keyword, value, comment, updateIfExists, new HashSet<>());
    }

    public AddNewRecordInputData(String keyword, Object value, String comment, boolean updateIfExists, Collection<File> fitsFiles) {
        super(OperationType.ADD_NEW_RECORD_TO_END, fitsFiles);
        this._keyword = keyword != null ? keyword.toUpperCase() : null;
        this._value = value;
        this._comment = comment;
        this._switches.put("updateIfExists", updateIfExists);
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

    public boolean updateIfExists() {
        return _switches.get("updateIfExists");
    }
}
