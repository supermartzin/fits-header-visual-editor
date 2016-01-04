package cz.muni.fi.fits.gui.view.controllers;

/**
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class AboutDialogController extends Controller {

    @Override
    public void init() {

    }

    public void openEnginePartWebpages() {
        openWebpage("https://github.com/supermartzin/fits-header-editor");
    }

    public void openGUIPartWebpages() {
        openWebpage("https://github.com/supermartzin/fits-header-visual-editor");
    }

    public void openNomTamFitsHomepage() {
        openWebpage("http://nom-tam-fits.github.io/nom-tam-fits/");
    }

    public void openGuiceHomepage() {
        openWebpage("https://github.com/google/guice");
    }

    public void openGuavaHomepage() {
        openWebpage("https://github.com/google/guava");
    }

    public void openFreepikIconsHomepage() {
        openWebpage("http://www.flaticon.com/");
    }

    private void openWebpage(String url) {
        if (url != null)
            _mainApp.getHostServices().showDocument(url);
    }
}
