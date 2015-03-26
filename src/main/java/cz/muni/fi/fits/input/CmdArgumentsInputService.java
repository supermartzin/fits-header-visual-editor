package cz.muni.fi.fits.input;

import cz.muni.fi.fits.exceptions.IllegalInputDataException;
import cz.muni.fi.fits.models.InputData;
import cz.muni.fi.fits.utils.LocaleHelper;

import javax.inject.Singleton;

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
            throw new IllegalInputDataException("No arguments provided");
        if (_cmdArgs.length < 2)
            throw new IllegalInputDataException("Insuffiecient number of parameters");

        String operation = _cmdArgs[0].trim().toUpperCase(LocaleHelper.getLocale());
        switch (operation) {
            case "ADD_BY_KW":
                return CmdArgsProcessingHelper.extractAddNewRecordData(_cmdArgs);
            case "ADD_BY_IX":
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
                throw new IllegalInputDataException("Unknown operation: '" + operation + "'");
        }

        return null;
    }
}