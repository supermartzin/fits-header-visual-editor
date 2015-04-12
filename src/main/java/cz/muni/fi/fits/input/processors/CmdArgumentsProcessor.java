package cz.muni.fi.fits.input.processors;

import cz.muni.fi.fits.exceptions.IllegalInputDataException;
import cz.muni.fi.fits.exceptions.UnknownOperationException;
import cz.muni.fi.fits.exceptions.WrongNumberOfParametersException;
import cz.muni.fi.fits.input.converters.TypeConverter;
import cz.muni.fi.fits.models.inputData.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.util.Collection;

/**
 *
 * TODO description
 */
@Singleton
public class CmdArgumentsProcessor implements InputProcessor {

    private TypeConverter _converter;
    private final String[] _cmdArgs;

    @Inject
    public void setTypeConverter(TypeConverter typeConverter) {
        _converter = typeConverter;
    }

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

        int fitsFilesArgIndex;
        InputData inputData;

        String operation = _cmdArgs[0].trim().toUpperCase();
        switch (operation) {
            case "ADD":
                inputData = CmdArgumentsProcessorHelper.extractAddNewRecordData(_cmdArgs, _converter);
                fitsFilesArgIndex = ((AddNewRecordInputData) inputData).updateIfExists() ? 2 : 1;
                break;

            case "ADD_IX":
                inputData = CmdArgumentsProcessorHelper.extractAddNewToIndexData(_cmdArgs, _converter);
                fitsFilesArgIndex = ((AddNewToIndexInputData) inputData).removeOldIfExists() ? 2 : 1;
                break;

            case "REMOVE":
                inputData = CmdArgumentsProcessorHelper.extractRemoveByKeywordData(_cmdArgs);
                fitsFilesArgIndex = 1;
                break;

            case "REMOVE_IX":
                inputData = CmdArgumentsProcessorHelper.extractRemoveFromIndexData(_cmdArgs);
                fitsFilesArgIndex = 1;
                break;

            case "CHANGE":
                inputData = CmdArgumentsProcessorHelper.extractChangeValueByKeywordData(_cmdArgs, _converter);
                fitsFilesArgIndex = ((ChangeValueByKeywordInputData) inputData).addNewIfNotExists() ? 2 : 1;
                break;

            case "CHANGE_KW":
                inputData = CmdArgumentsProcessorHelper.extractChangeKeywordData(_cmdArgs);
                fitsFilesArgIndex = ((ChangeKeywordInputData) inputData).removeValueOfNewIfExists() ? 2 : 1;
                break;

            case "CHAIN":
                inputData = CmdArgumentsProcessorHelper.extractChainRecordsData(_cmdArgs);
                ChainRecordsInputData crid = (ChainRecordsInputData) inputData;
                if (crid.updateIfExists() && crid.skipIfChainKwNotExists())
                    fitsFilesArgIndex = 3;
                else if (!crid.updateIfExists() && !crid.skipIfChainKwNotExists())
                    fitsFilesArgIndex = 1;
                else
                    fitsFilesArgIndex = 2;
                break;

            default:
                throw new UnknownOperationException(operation, "Unknown operation '" + operation + "'");
        }

        // get input FITS files
        Collection<File> fitsFiles = CmdArgumentsProcessorHelper.extractFilesData(_cmdArgs[fitsFilesArgIndex]);
        inputData.setFitsFiles(fitsFiles);

        return inputData;
    }
}