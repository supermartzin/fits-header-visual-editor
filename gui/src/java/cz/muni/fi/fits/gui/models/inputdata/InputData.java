package cz.muni.fi.fits.gui.models.inputdata;

/**
 * Interface for creating classes storing input data
 * for various FITS Header editing operations
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public interface InputData {

    /**
     * Creates {@link String} containing all input data
     * in required order and format for use as command line
     * argument in engine program
     *
     * @return  {@link String} with ordered and formatted input data
     */
    String getInputDataString();

    /**
     * Sets a path to file which contains paths to FITS files
     * used for editing operation
     *
     * @param filePath  path to existing file with FITS files paths
     */
    void setInputFilePath(String filePath);
}
