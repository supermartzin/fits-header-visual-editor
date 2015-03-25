package cz.muni.fi.fits.input;

import cz.muni.fi.fits.models.InputData;

import javax.inject.Singleton;

/**
 *
 * TODO description
 */
@Singleton
public class CmdArgumentsInputService implements InputService {

    private final String[] cmdArgs;

    public CmdArgumentsInputService(String[] cmdArgs) {
        this.cmdArgs = cmdArgs;
    }

    @Override
    public InputData getProcessedInput() {
        int result = cmdArgs.length + 2;
        return null;
    }
}