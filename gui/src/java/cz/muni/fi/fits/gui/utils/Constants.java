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
    public static final Pattern TIME_PATTERN = Pattern.compile("^[0-9:.]{1,12}$");
    public static final Pattern DECIMAL_NUMBER_PATTERN = Pattern.compile("^[0-9.]*$");
    public static final Pattern INTEGRAL_NUMBER_PATTERN = Pattern.compile("^\\d{1,5}$");
    public static final Pattern KEYWORD_PATTERN = Pattern.compile("[a-zA-Z0-9_-]{1,8}");
}
