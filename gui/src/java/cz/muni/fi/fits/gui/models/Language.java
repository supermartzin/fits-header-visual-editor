package cz.muni.fi.fits.gui.models;

import java.util.Locale;

/**
 * TODO description
 */
public enum Language {
    ENGLISH("en", Locale.ENGLISH),
    SLOVAK("sk", Locale.forLanguageTag("sk")),
    UNKNOWN("", Locale.ROOT);

    private final String _code;
    private final Locale _locale;

    Language(String code, Locale locale) {
        _code = code;
        _locale = locale;
    }

    public String getCode() {
        return _code;
    }

    public Locale getLocale() {
        return _locale;
    }

    public static Language getLanguageByCode(String code) {
        if (ENGLISH.getCode().equals(code))
            return ENGLISH;
        if (SLOVAK.getCode().equals(code))
            return SLOVAK;

        return UNKNOWN;
    }
}
