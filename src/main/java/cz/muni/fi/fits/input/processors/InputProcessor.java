package cz.muni.fi.fits.input.processors;

import cz.muni.fi.fits.exceptions.IllegalInputDataException;
import cz.muni.fi.fits.models.inputData.InputData;

/**
 *
 * TODO description
 */
public interface InputProcessor {

    /**
     *
     * @return
     * @throws IllegalInputDataException
     */
    InputData getProcessedInput() throws IllegalInputDataException;
}
