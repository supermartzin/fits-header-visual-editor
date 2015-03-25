package cz.muni.fi.fits;

import com.google.inject.AbstractModule;
import cz.muni.fi.fits.engine.core.HeaderEditingService;
import cz.muni.fi.fits.engine.core.NomTamHeaderEditor;
import cz.muni.fi.fits.input.CmdArgumentsInputService;
import cz.muni.fi.fits.input.InputService;
import cz.muni.fi.fits.output.OutputService;
import cz.muni.fi.fits.output.writers.ConsoleOutputWriter;

/**
 *
 * TODO description
 */
public class AppInjector extends AbstractModule {
    private final Object inputData;

    public AppInjector(Object inputData) {
        this.inputData = inputData;
    }

    @Override
    protected void configure() {
        bind(OutputService.class).to(ConsoleOutputWriter.class);
        bind(HeaderEditingService.class).to(NomTamHeaderEditor.class);
        bind(InputService.class).toInstance(new CmdArgumentsInputService((String[]) this.inputData));
    }
}
