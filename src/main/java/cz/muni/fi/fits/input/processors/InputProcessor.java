package cz.muni.fi.fits.input.processors;

import cz.muni.fi.fits.exceptions.IllegalInputDataException;
import cz.muni.fi.fits.models.InputData;

/**
 *
 * TODO description
 */
public interface InputProcessor {

    InputData getProcessedInput() throws IllegalInputDataException;
}
