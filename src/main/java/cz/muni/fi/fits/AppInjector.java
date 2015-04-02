package cz.muni.fi.fits;

import com.google.inject.AbstractModule;
import cz.muni.fi.fits.engine.HeaderEditingEngine;
import cz.muni.fi.fits.engine.NomTamFitsEditingEngine;
import cz.muni.fi.fits.input.processors.CmdArgumentsProcessor;
import cz.muni.fi.fits.input.processors.InputProcessor;
import cz.muni.fi.fits.input.validators.DefaultInputDataValidator;
import cz.muni.fi.fits.input.validators.InputDataValidator;
import cz.muni.fi.fits.output.writers.ConsoleOutputWriter;
import cz.muni.fi.fits.output.writers.OutputWriter;

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
        bind(OutputWriter.class).to(ConsoleOutputWriter.class);
        bind(HeaderEditingEngine.class).to(NomTamFitsEditingEngine.class);
        bind(InputDataValidator.class).to(DefaultInputDataValidator.class);
        bind(InputProcessor.class).toInstance(new CmdArgumentsProcessor((String[]) _inputData));
    }
}
