package cz.muni.fi.fits.engine;

import cz.muni.fi.fits.engine.core.HeaderEditingService;
import cz.muni.fi.fits.exceptions.IllegalInputDataException;
import cz.muni.fi.fits.input.processors.InputProcessor;
import cz.muni.fi.fits.input.validators.InputDataValidator;
import cz.muni.fi.fits.models.inputData.InputData;
import cz.muni.fi.fits.output.OutputService;

import javax.inject.Inject;

/**
 *
 * TODO description
 */
public class FITSHeaderEditor {

    private final HeaderEditingService _headerEditingService;
    private final InputProcessor _inputProcessor;
    private final InputDataValidator _inputDataValidator;
    private final OutputService _outputService;

    @Inject
    public FITSHeaderEditor(HeaderEditingService headerEditingService, InputProcessor inputProcessor, InputDataValidator inputDataValidator, OutputService outputService) {
        this._headerEditingService = headerEditingService;
        this._inputProcessor = inputProcessor;
        this._inputDataValidator = inputDataValidator;
        this._outputService = outputService;
    }

    public void start() {
        try {
            InputData inputData = _inputProcessor.getProcessedInput();
        } catch (IllegalInputDataException e) {
            e.printStackTrace();
        }
    }
}
