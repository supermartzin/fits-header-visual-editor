package cz.muni.fi.fits.output.writers;

/**
 *
 * TODO description
 */
public interface OutputWriter {

    /**
     *
     * @param infoMessage
     * @return
     */
    boolean writeInfo(String infoMessage);

    /**
     *
     * @param exception
     * @return
     */
    boolean writeException(Throwable exception);

    /**
     *
     * @param errorMessage
     * @param exception
     * @return
     */
    boolean writeException(String errorMessage, Throwable exception);
}
