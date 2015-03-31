package cz.muni.fi.fits.models.inputData;

import cz.muni.fi.fits.models.OperationType;

import java.io.File;
import java.util.Collection;

/**
 * TODO description
 */
public abstract class OneSwitchInputData extends InputData {

    protected final boolean _hasSwitch;

    public OneSwitchInputData(OperationType operationType, Collection<File> fitsFiles) {
        this(operationType, fitsFiles, false);
    }

    public OneSwitchInputData(OperationType operationType, Collection<File> fitsFiles, boolean hasSwitch) {
        super(operationType, fitsFiles);
        this._hasSwitch = hasSwitch;
    }
}
