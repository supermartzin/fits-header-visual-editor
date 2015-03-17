package cz.muni.fi.fits.engine;

import cz.muni.fi.fits.engine.core.HeaderEditingService;
import cz.muni.fi.fits.input.InputService;
import cz.muni.fi.fits.output.OutputService;

/**
 *
 * TODO description
 */
public class FITSHeaderEditor {

    private final HeaderEditingService headerEditingService;
    private final InputService inputService;
    private final OutputService outputService;

    public FITSHeaderEditor(HeaderEditingService headerEditingService, InputService inputService, OutputService outputService) {
        this.headerEditingService = headerEditingService;
        this.inputService = inputService;
        this.outputService = outputService;
    }


}
