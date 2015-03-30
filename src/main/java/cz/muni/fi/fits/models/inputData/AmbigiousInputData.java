package cz.muni.fi.fits.models.inputData;

import cz.muni.fi.fits.models.OperationType;

import java.io.File;
import java.util.Collection;

/**
 * TODO description
 */
public abstract class AmbigiousInputData extends InputData {

    protected final boolean _hasSwitch;

    public AmbigiousInputData(OperationType operationType, Collection<File> fitsFiles) {
        this(operationType, fitsFiles, false);
    }

    public AmbigiousInputData(OperationType operationType, Collection<File> fitsFiles, boolean hasSwitch) {
        super(operationType, fitsFiles);
        this._hasSwitch = hasSwitch;
    }
}
