package cz.muni.fi.fits.input.processors;

import cz.muni.fi.fits.exceptions.IllegalInputDataException;
import cz.muni.fi.fits.exceptions.UnknownOperationException;
import cz.muni.fi.fits.exceptions.WrongNumberOfParametersException;
import cz.muni.fi.fits.models.InputData;
import cz.muni.fi.fits.models.inputData.AddNewRecordInputData;
import cz.muni.fi.fits.models.inputData.AddNewToIndexInputData;
import cz.muni.fi.fits.utils.LocaleHelper;

import javax.inject.Singleton;
import java.io.File;
import java.util.Collection;

/**
 *
 * TODO description
 */
@Singleton
public class CmdArgumentsProcessor implements InputProcessor {

    private final String[] _cmdArgs;

    public CmdArgumentsProcessor(String[] cmdArgs) {
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
        Collection<File> fitsFiles = CmdArgumentsProcessorHelper.extractFilesData(_cmdArgs[1]);

        switch (operation) {
            case "ADD_BY_KW":
                AddNewRecordInputData addNewRecordInputData = CmdArgumentsProcessorHelper.extractAddNewRecordData(_cmdArgs);
                addNewRecordInputData.setFitsFiles(fitsFiles);
                return addNewRecordInputData;

            case "ADD_TO_IX":
                AddNewToIndexInputData addNewToIndexInputData = CmdArgumentsProcessorHelper.extractAddNewToIndexData(_cmdArgs);
                addNewToIndexInputData.setFitsFiles(fitsFiles);
                return addNewToIndexInputData;

            case "REMOVE_BY_KW":
                return null;

            case "REMOVE_BY_IX":
                return null;

            case "CHANGE_KW":
                return null;

            case "CHANGE_VALUE":
                return null;

            case "CHAIN_NEW":
                return null;

            case "CHAIN_EDIT":
                return null;

            default:
                throw new UnknownOperationException(operation, "Unknown operation");
        }
    }
}