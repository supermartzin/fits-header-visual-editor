package cz.muni.fi.fits.input;

import cz.muni.fi.fits.exceptions.IllegalInputDataException;
import cz.muni.fi.fits.models.InputData;

/**
 *
 * TODO description
 */
public interface InputService {

    InputData getProcessedInput() throws IllegalInputDataException;
}
