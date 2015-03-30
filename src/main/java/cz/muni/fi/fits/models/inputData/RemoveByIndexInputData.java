package cz.muni.fi.fits.models.inputData;

import cz.muni.fi.fits.models.OperationType;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

/**
 * TODO description
 */
public class RemoveByIndexInputData extends InputData {

    private final int _index;

    public RemoveByIndexInputData(int index) {
        this(index, new HashSet<>());
    }

    public RemoveByIndexInputData(int index, Collection<File> fitsFiles) {
        super(OperationType.REMOVE_RECORD_BY_INDEX, fitsFiles);
        this._index = index;
    }

    public int getIndex() {
        return _index;
    }
}
