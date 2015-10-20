package cz.muni.fi.fits.gui.utils;

import cz.muni.fi.fits.gui.models.Language;

import java.util.regex.Pattern;

/**
 * TODO description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public final class Constants {

    public static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    public static final Pattern TIME_PATTERN = Pattern.compile("^[\\d:.]{1,12}$");
    public static final Pattern DECIMAL_NUMBER_PATTERN = Pattern.compile("^\\d*(\\.\\d*)?$");
    public static final Pattern INTEGRAL_NUMBER_PATTERN = Pattern.compile("^\\d{1,5}$");
    public static final Pattern KEYWORD_PATTERN = Pattern.compile("[a-zA-Z\\d_-]{1,8}");
    public static final Pattern HOURS_PATTERN = Pattern.compile("^\\d|1\\d|2[0-3]$");
    public static final Pattern DEGREES_PATTERN = Pattern.compile("^-|-?(\\d|[0-8]\\d|90)$");
    public static final Pattern MINUTES_PATTERN = Pattern.compile("^\\d|[1-5]\\d$");
    public static final Pattern SECONDS_PATTERN = Pattern.compile("^\\d|[1-5]\\d$");
    public static final Pattern RIGHT_ASCENSION_PATTERN = Pattern.compile("^(([1-2]?\\d?\\d)|(3[0-5]\\d))(\\.\\d*)?|360$");
    public static final Pattern DECLINATION_PATTERN = Pattern.compile("^-|-?((\\d|[1-8]\\d)(\\.\\d*)?|90)$");

    public static final String INPUT_FILES_PLACEHOLDER = "<<files>>";
    public static final String EXPRESSIONS_DELIMITER = " ";
}
