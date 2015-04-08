package cz.muni.fi.fits.models.inputData;

import cz.muni.fi.fits.models.OperationType;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * TODO description
 */
public class AddNewToIndexInputData extends SwitchInputData {

    private final int _index;
    private final String _keyword;
    private final Object _value;
    private final String _comment;

    public AddNewToIndexInputData(int index, String keyword, Object value, String comment, boolean removeOldIfExists) {
        this(index, keyword, value, comment, removeOldIfExists, new HashSet<>());
    }

    public AddNewToIndexInputData(int index, String keyword, Object value, String comment, boolean removeOldIfExists, Collection<File> fitsFiles) {
        super(OperationType.ADD_NEW_RECORD_TO_INDEX, fitsFiles);
        this._index = index;
        this._keyword = keyword.toUpperCase();
        this._value = value;
        this._comment = comment;
        this._switches.put("removeOldIfExists", removeOldIfExists);
    }

    public int getIndex() {
        return this._index;
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

    public boolean removeOldIfExists() {
        return _switches.get("removeOldIfExists");
    }
}