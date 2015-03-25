package cz.muni.fi.fits;

import com.google.inject.Guice;
import com.google.inject.Injector;
import cz.muni.fi.fits.engine.FITSHeaderEditor;

/**
 *
 * TODO description
 */
public class ApplicationInitializer {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppInjector(args));

        FITSHeaderEditor editor = injector.getInstance(FITSHeaderEditor.class);

        editor.start();
    }
}
