package cz.muni.fi.fits.models.inputData;

import cz.muni.fi.fits.models.OperationType;

import java.io.File;
import java.util.Collection;

/**
 *
 * TODO description
 */
public class TwoSwitchesInputData extends InputData {

    protected final boolean _hasFirstSwitch;
    protected final boolean _hasSecondSwitch;

    public TwoSwitchesInputData(OperationType operationType, Collection<File> fitsFiles, boolean hasFirstSwitch, boolean hasSecondSwitch) {
        super(operationType, fitsFiles);
        this._hasFirstSwitch = hasFirstSwitch;
        this._hasSecondSwitch = hasSecondSwitch;
    }
}
