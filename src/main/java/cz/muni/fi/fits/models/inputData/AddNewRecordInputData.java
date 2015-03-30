package cz.muni.fi.fits.models.inputData;

import cz.muni.fi.fits.models.InputData;
import cz.muni.fi.fits.models.OperationType;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * TODO description
 */
public class AddNewRecordInputData extends InputData {

    private final String _keyword;
    private final String _value;
    private final String _comment;

    public AddNewRecordInputData(String keyword, String value, String comment) {
        this(keyword, value, comment, new HashSet<>());
    }

    public AddNewRecordInputData(String keyword, String value, String comment, Collection<File> fitsFiles) {
        super(OperationType.ADD_NEW_RECORD_TO_END, fitsFiles);
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
}
