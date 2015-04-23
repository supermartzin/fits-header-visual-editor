package cz.muni.fi.fits.utils;

/**
 * Static class contaning useful constants related to FITS files
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public final class Constants {

    public static final int MAX_KEYWORD_LENGTH = 8;
    public static final int MAX_COMMENT_LENGTH = 47;
    public static final int MAX_STRING_VALUE_LENGTH = 68;
    public static final int MAX_STRING_VALUE_COMMENT_LENGTH = 65;
    public static final String KEYWORD_REGEX = "[A-Z0-9_-]+";
    public static final String DEFAULT_HJD_COMMENT = "center of exposure";
    public static final String DEFAULT_JD_COMMENT = "center of exposure";
}
