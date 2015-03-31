package cz.muni.fi.fits.models.inputData;

import cz.muni.fi.fits.models.OperationType;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * TODO description
 */
public class AddNewToIndexInputData extends OneSwitchInputData {

    private final int _index;
    private final String _keyword;
    private final String _value;
    private final String _comment;

    public AddNewToIndexInputData(int index, String keyword, String value, String comment, boolean removeOldIfExists) {
        this(index, keyword, value, comment, removeOldIfExists, new HashSet<>());
    }

    public AddNewToIndexInputData(int index, String keyword, String value, String comment, boolean removeOldIfExists, Collection<File> fitsFiles) {
        super(OperationType.ADD_NEW_RECORD_TO_INDEX, fitsFiles, removeOldIfExists);
        this._index = index;
        this._keyword = keyword.toUpperCase();
        this._value = value;
        this._comment = comment;
    }

    public int getIndex() {
        return this._index;
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

    public boolean removeOldIfExists() {
        return _hasSwitch;
    }
}
