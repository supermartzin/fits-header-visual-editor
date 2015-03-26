package cz.muni.fi.fits.models;

import java.io.File;
import java.util.Collection;

/**
 *
 * TODO description
 */
public abstract class InputData {

    private OperationType _operationType;
    private Collection<File> _fitsFiles;

    public OperationType getOperationType() {
        return _operationType;
    }

    public void setOperationType(OperationType operationType) {
        this._operationType = operationType;
    }

    public Collection<File> getFitsFiles() {
        return _fitsFiles;
    }

    public void setFitsFiles(Collection<File> fitsFiles) {
        this._fitsFiles = fitsFiles;
    }
}