package cz.muni.fi.fits.input.processors;

import cz.muni.fi.fits.exceptions.IllegalInputDataException;
import cz.muni.fi.fits.exceptions.UnknownOperationException;
import cz.muni.fi.fits.exceptions.WrongNumberOfParametersException;
import cz.muni.fi.fits.models.inputData.InputData;
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
        if (_cmdArgs.length == 2 && _cmdArgs[1].startsWith("-"))
            throw new WrongNumberOfParametersException(_cmdArgs.length, "Insufficient number of parameters");

        String operation = _cmdArgs[0].trim().toUpperCase(LocaleHelper.getLocale());

        InputData inputData;
        switch (operation) {
            case "ADD":
                inputData = CmdArgumentsProcessorHelper.extractAddNewRecordData(_cmdArgs);
                break;
            case "ADD_IX":
                inputData = CmdArgumentsProcessorHelper.extractAddNewToIndexData(_cmdArgs);
                break;
            case "REMOVE":
                inputData = CmdArgumentsProcessorHelper.extractRemoveByKeywordData(_cmdArgs);
                break;
            case "REMOVE_IX":
                inputData = CmdArgumentsProcessorHelper.extractRemoveByIndexData(_cmdArgs);
                break;
            case "CHANGE":
                inputData = CmdArgumentsProcessorHelper.extractChangeValueByKeywordData(_cmdArgs);
                break;
            case "CHANGE_KW":
                inputData = CmdArgumentsProcessorHelper.extractChangeKeywordData(_cmdArgs);
                break;
            case "CHAIN":
                inputData = CmdArgumentsProcessorHelper.extractChainRecordsData(_cmdArgs);
                break;
            default:
                throw new UnknownOperationException(operation, "Unknown operation");
        }

        // get input FITS files
        Collection<File> fitsFiles;
        if (!_cmdArgs[1].startsWith("-"))
            fitsFiles = CmdArgumentsProcessorHelper.extractFilesData(_cmdArgs[1]);
        else
            fitsFiles = CmdArgumentsProcessorHelper.extractFilesData(_cmdArgs[2]);
        inputData.setFitsFiles(fitsFiles);

        return inputData;
    }
}