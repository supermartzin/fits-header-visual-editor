package cz.muni.fi.fits;

import com.google.inject.AbstractModule;
import cz.muni.fi.fits.input.CmdArgumnetsInputService;
import cz.muni.fi.fits.input.InputService;
import cz.muni.fi.fits.output.OutputService;
import cz.muni.fi.fits.output.writers.ConsoleOutputWriter;

/**
 *
 * TODO description
 */
public class AppInjector extends AbstractModule {

    @Override
    protected void configure() {
        bind(OutputService.class).to(ConsoleOutputWriter.class);
        bind(InputService.class).to(CmdArgumnetsInputService.class);
    }
}
