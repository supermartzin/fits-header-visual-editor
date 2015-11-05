package cz.muni.fi.fits.gui.listeners;

/**
 * TODO insert description
 *
 * @author Martin Vrábel - © 2015 FITS-HeaderVisualEditor
 * @version 1.0
 */
public interface OutputListener {

    /**
     *
     * @param infoMessage
     */
    void onInfo(String infoMessage);

    /**
     *
     * @param errorMessage
     */
    void onError(String errorMessage);
}