package cz.muni.fi.fits;

import com.google.inject.AbstractModule;
import cz.muni.fi.fits.engine.core.HeaderEditingService;
import cz.muni.fi.fits.engine.core.NomTamHeaderEditor;
import cz.muni.fi.fits.input.processors.CmdArgumentsProcessor;
import cz.muni.fi.fits.input.processors.InputProcessor;
import cz.muni.fi.fits.input.validators.DefaultInputValidator;
import cz.muni.fi.fits.input.validators.InputValidator;
import cz.muni.fi.fits.output.OutputService;
import cz.muni.fi.fits.output.writers.ConsoleOutputWriter;

/**
 *
 * TODO description
 */
public class AppInjector extends AbstractModule {
    private final Object _inputData;

    public AppInjector(Object inputData) {
        this._inputData = inputData;
    }

    @Override
    protected void configure() {
        bind(OutputService.class).to(ConsoleOutputWriter.class);
        bind(HeaderEditingService.class).to(NomTamHeaderEditor.class);
        bind(InputValidator.class).to(DefaultInputValidator.class);
        bind(InputProcessor.class).toInstance(new CmdArgumentsProcessor((String[]) _inputData));
    }
}
