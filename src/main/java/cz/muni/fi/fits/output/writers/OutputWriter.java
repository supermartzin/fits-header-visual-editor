package cz.muni.fi.fits.output.writers;

import java.io.File;

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
     * @param file
     * @param infoMessage
     * @return
     */
    boolean writeInfo(File file, String infoMessage);

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

    /**
     *
     * @param file
     * @param exception
     * @return
     */
    boolean writeException(File file, Throwable exception);
}
