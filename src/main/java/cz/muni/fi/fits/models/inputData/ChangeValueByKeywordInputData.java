package cz.muni.fi.fits.models.inputData;

import cz.muni.fi.fits.models.OperationType;

import java.io.File;
import java.util.Collection;

/**
 *
 * TODO description
 */
public class ChangeValueByKeywordInputData extends InputData {
    public ChangeValueByKeywordInputData(Collection<File> fitsFiles) {
        super(OperationType.CHANGE_VALUE_BY_KEYWORD, fitsFiles);
    }
}
