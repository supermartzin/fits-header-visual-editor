package cz.muni.fi.fits.gui.utils;

import cz.muni.fi.fits.gui.models.Language;

import java.io.File;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

/**
 * TODO description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public final class Constants {

    public static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public static final String ENGINE_PROPERTIES_FILENAME = "fits.properties";
    public static final String PATH_SEPARATOR = File.separator;
    public static final String NEWLINE = System.getProperty("line.separator");

    public static final String INFO_IDENTIFIER = " INFO >>";
    public static final String ERROR_IDENTIFIER = " ERROR >>";
    public static final String EXCEPTION_IDENTIFIER = " EXCEPTION >>";

    public static final Pattern TIME_PATTERN = Pattern.compile("^[\\d:.]{1,12}$");
    public static final Pattern INDEX_PATTERN = Pattern.compile("^[1-9]\\d*$");
    public static final Pattern DECIMAL_NUMBER_PATTERN = Pattern.compile("^\\d*(\\.\\d*)?$");
    public static final Pattern NONNEG_INTEGRAL_NUMBER_PATTERN = Pattern.compile("^\\d{1,5}$");
    public static final Pattern KEYWORD_PATTERN = Pattern.compile("[a-zA-Z\\d_-]{1,8}");
    public static final Pattern HOURS_PATTERN = Pattern.compile("^\\d|1\\d|2[0-3]$");
    public static final Pattern DEGREES_PATTERN = Pattern.compile("^-|-?(\\d|[0-8]\\d|90)$");
    public static final Pattern MINUTES_PATTERN = Pattern.compile("^\\d|[1-5]\\d$");
    public static final Pattern SECONDS_PATTERN = Pattern.compile("^\\d|[1-5]\\d$");
    public static final Pattern RIGHT_ASCENSION_PATTERN = Pattern.compile("^(([1-2]?\\d?\\d)|(3[0-5]\\d))(\\.\\d*)?|360$");
    public static final Pattern DECLINATION_PATTERN = Pattern.compile("^-|-?((\\d|[1-8]\\d)(\\.\\d*)?|90)$");

    public static final Pattern OUTPUT_DATETIME_PATTERN = Pattern.compile("^(\\[\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d{0,3})?\\])");
    public static final Pattern OUTPUT_FITS_FILE_PATTERN = Pattern.compile(">> \\[(.+?)\\]: ");
}
