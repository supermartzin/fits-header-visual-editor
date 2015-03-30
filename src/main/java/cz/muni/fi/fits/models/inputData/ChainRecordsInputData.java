package cz.muni.fi.fits.models.inputData;

import cz.muni.fi.fits.models.OperationType;

import java.io.File;
import java.util.Collection;

/**
 *
 * TODO description
 */
public class ChainRecordsInputData extends InputData {
    public ChainRecordsInputData(Collection<File> fitsFiles) {
        super(OperationType.CHAIN_RECORDS, fitsFiles);
    }
}
