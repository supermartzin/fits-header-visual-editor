package cz.muni.fi.fits.input;

import cz.muni.fi.fits.exceptions.UnknownOperationException;
import cz.muni.fi.fits.exceptions.WrongNumberOfParametersException;
import cz.muni.fi.fits.exceptions.IllegalInputDataException;
import cz.muni.fi.fits.models.InputData;
import cz.muni.fi.fits.models.OperationType;
import cz.muni.fi.fits.models.inputDataModels.AddNewRecordInputData;
import cz.muni.fi.fits.utils.LocaleHelper;

import javax.inject.Singleton;
import java.io.File;
import java.util.Collection;

/**
 *
 * TODO description
 */
@Singleton
public class CmdArgumentsInputService implements InputService {

    private final String[] _cmdArgs;

    public CmdArgumentsInputService(String[] cmdArgs) {
        this._cmdArgs = cmdArgs;
    }

    @Override
    public InputData getProcessedInput() throws IllegalInputDataException {
        if (_cmdArgs == null)
            throw new IllegalInputDataException("Arguments are null");
        if (_cmdArgs.length == 0)
            throw new WrongNumberOfParametersException(0, "No arguments provided");
        if (_cmdArgs.length < 2)
            throw new WrongNumberOfParametersException(_cmdArgs.length, "Insufficient number of parameters");

        String operation = _cmdArgs[0].trim().toUpperCase(LocaleHelper.getLocale());

        // get input FITS files
        Collection<File> fitsFiles = CmdArgsProcessingHelper.extractFilesData(_cmdArgs[1]);

        switch (operation) {
            case "ADD_BY_KW":
                AddNewRecordInputData inputData = CmdArgsProcessingHelper.extractAddNewRecordData(_cmdArgs);
                inputData.setOperationType(OperationType.ADD_NEW_RECORD_TO_END);
                inputData.setFitsFiles(fitsFiles);
            case "ADD_TO_IX":
                break;
            case "REMOVE_BY_KW":
                break;
            case "REMOVE_BY_IX":
                break;
            case "CHANGE_KW":
                break;
            case "CHANGE_VALUE":
                break;
            case "CHAIN_NEW":
                break;
            case "CHAIN_EDIT":
                break;
            default:
                throw new UnknownOperationException(operation, "Unknown operation");
        }

        return null;
    }
}