package cz.muni.fi.fits.models.inputData;

import cz.muni.fi.fits.models.OperationType;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * TODO description
 */
public abstract class InputData {

    private final OperationType _operationType;
    private Collection<File> _fitsFiles;

    public InputData(OperationType operationType) {
        this(operationType, new HashSet<>());
    }

    public InputData(OperationType operationType, Collection<File> fitsFiles) {
        this._operationType = operationType;
        this._fitsFiles = fitsFiles;
    }

    public OperationType getOperationType() {
        return _operationType;
    }

    public Collection<File> getFitsFiles() {
        return _fitsFiles;
    }

    public void setFitsFiles(Collection<File> fitsFiles) {
        this._fitsFiles = fitsFiles;
    }
}