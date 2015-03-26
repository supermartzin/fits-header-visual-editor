package cz.muni.fi.fits.models.inputDataModels;

import cz.muni.fi.fits.models.OperationType;

import java.io.File;
import java.util.Collection;

/**
 *
 * TODO description
 */
public class AddNewToIndexInputData extends AddNewRecordInputData {

    private final int index;

    public AddNewToIndexInputData(int index, String keyword, String value, String comment) {
        super(keyword, value, comment);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
