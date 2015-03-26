package cz.muni.fi.fits.models.inputDataModels;

import cz.muni.fi.fits.models.InputData;
import cz.muni.fi.fits.models.OperationType;
import cz.muni.fi.fits.utils.LocaleHelper;

import java.io.File;
import java.util.Collection;

/**
 *
 * TODO description
 */
public class AddNewRecordInputData extends InputData {

    private final String _keyword;
    private final String _value;
    private final String _comment;

    public AddNewRecordInputData(String keyword, String value, String comment) {
        this._value = value;
        this._comment = comment;
        this._keyword = keyword.toUpperCase(LocaleHelper.getLocale());
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
