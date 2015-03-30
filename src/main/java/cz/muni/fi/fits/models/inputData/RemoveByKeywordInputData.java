package cz.muni.fi.fits.models.inputData;

import cz.muni.fi.fits.models.OperationType;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * TODO description
 */
public class RemoveByKeywordInputData extends InputData {

    private final String _keyword;

    public RemoveByKeywordInputData(String keyword) {
        this(keyword, new HashSet<>());
    }

    public RemoveByKeywordInputData(String keyword, Collection<File> fitsFiles) {
        super(OperationType.REMOVE_RECORD_BY_KEYWORD, fitsFiles);
        this._keyword = keyword;
    }

    public String getKeyword() {
        return _keyword;
    }
}
