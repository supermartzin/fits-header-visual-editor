package cz.muni.fi.fits;

import com.google.inject.AbstractModule;
import cz.muni.fi.fits.engine.HeaderEditingEngine;
import cz.muni.fi.fits.engine.NomTamFitsEditingEngine;
import cz.muni.fi.fits.input.converters.TypeConverter;
import cz.muni.fi.fits.input.converters.ValueTypeConverter;
import cz.muni.fi.fits.input.processors.CmdArgumentsProcessor;
import cz.muni.fi.fits.input.processors.InputProcessor;
import cz.muni.fi.fits.input.validators.DefaultInputDataValidator;
import cz.muni.fi.fits.input.validators.InputDataValidator;
import cz.muni.fi.fits.output.writers.FileConsoleOutputWriter;
import cz.muni.fi.fits.output.writers.OutputWriter;

/**
 * Class to inject all defined dependencies with Google Guice
 *
 * @author Martin Vrábel
 * @version 1.0.1
 */
public class AppInjector extends AbstractModule {
    private final Object _inputData;

    public AppInjector(Object inputData) {
        this._inputData = inputData;
    }

    @Override
    protected void configure() {
        bind(OutputWriter.class).toInstance(new FileConsoleOutputWriter("output.txt"));
        bind(HeaderEditingEngine.class).to(NomTamFitsEditingEngine.class);
        bind(InputDataValidator.class).to(DefaultInputDataValidator.class);
        bind(TypeConverter.class).to(ValueTypeConverter.class);
        bind(InputProcessor.class).toInstance(new CmdArgumentsProcessor((String[]) _inputData));
    }
}
