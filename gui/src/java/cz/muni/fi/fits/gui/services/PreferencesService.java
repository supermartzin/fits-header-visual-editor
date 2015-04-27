package cz.muni.fi.fits.gui.services;

import cz.muni.fi.fits.gui.models.Language;
import cz.muni.fi.fits.gui.utils.Constants;

import java.util.prefs.Preferences;

/**
 * TODO description
 */
public class PreferencesService {

    public static Language getLanguage(Class<?> resourceClass) {
        // load preferences
        Preferences preferences = Preferences.userNodeForPackage(resourceClass.getClass());
        // load language
        String lang = preferences.get("lang", "");
        if (!lang.isEmpty()) {
            return Language.getLanguageByCode(lang);
        } else {
            // set and return default language
            preferences.put("lang", Constants.DEFAULT_LANGUAGE.getCode());
            return Constants.DEFAULT_LANGUAGE;
        }
    }

    public static void saveLanguage(Language language, Class<?> resourceClass) {
        // load preferences
        Preferences preferences = Preferences.userNodeForPackage(resourceClass.getClass());
        // load language
        preferences.put("lang", language.getCode());
    }
}
