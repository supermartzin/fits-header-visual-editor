package cz.muni.fi.fits.engine;

import cz.muni.fi.fits.engine.core.HeaderEditingService;
import cz.muni.fi.fits.input.InputService;
import cz.muni.fi.fits.models.InputData;
import cz.muni.fi.fits.output.OutputService;

import javax.inject.Inject;

/**
 *
 * TODO description
 */
public class FITSHeaderEditor {

    private final HeaderEditingService headerEditingService;
    private final InputService inputService;
    private final OutputService outputService;

    @Inject
    public FITSHeaderEditor(HeaderEditingService headerEditingService, InputService inputService, OutputService outputService) {
        this.headerEditingService = headerEditingService;
        this.inputService = inputService;
        this.outputService = outputService;
    }

    public void start() {
        InputData inputData = inputService.getProcessedInput();
    }
}
