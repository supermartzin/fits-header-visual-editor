package cz.muni.fi.fits.gui.services;

import cz.muni.fi.fits.gui.models.Language;
import cz.muni.fi.fits.gui.utils.Constants;

import java.util.prefs.Preferences;

/**
 * TODO description
 */
public class PreferencesService {

    private static final String LANGUAGE_KEY = "lang";
    private static final String FILEPATH_KEY = "filepath";

    /**
     *
     * @param resourceClass
     * @return
     */
    public static Language getLanguage(Class<?> resourceClass) {
        // load preferences
        Preferences preferences = Preferences.userNodeForPackage(resourceClass.getClass());
        // load language
        String lang = preferences.get(LANGUAGE_KEY, "");
        if (!lang.isEmpty()) {
            return Language.getLanguageByCode(lang);
        } else {
            // save and return default language
            preferences.put(LANGUAGE_KEY, Constants.DEFAULT_LANGUAGE.getCode());
            return Constants.DEFAULT_LANGUAGE;
        }
    }

    /**
     *
     * @param language
     * @param resourceClass
     */
    public static void saveLanguage(Language language, Class<?> resourceClass) {
        // load preferences
        Preferences preferences = Preferences.userNodeForPackage(resourceClass.getClass());
        // save language
        preferences.put(LANGUAGE_KEY, language.getCode());
    }

    /**
     *
     * @param resourceClass
     * @return
     */
    public static String loadEngineFilePath(Class<?> resourceClass) {
        // load preferences
        Preferences preferences = Preferences.userNodeForPackage(resourceClass.getClass());
        // load filepath
        String filepath = preferences.get(FILEPATH_KEY, "");
        if (!filepath.isEmpty()) {
            return filepath;
        } else {
            // save and return default language
            preferences.put(FILEPATH_KEY, Constants.DEFAULT_ENGINE_FILEPATH);
            return Constants.DEFAULT_ENGINE_FILEPATH;
        }
    }

    /**
     *
     * @param filepath
     * @param resourceClass
     */
    public static void saveEngineFilePath(String filepath, Class<?> resourceClass) {
        // load preferences
        Preferences preferences = Preferences.userNodeForPackage(resourceClass.getClass());
        // save filepath
        if (filepath != null && !filepath.isEmpty())
            preferences.put(FILEPATH_KEY, filepath);
        else
            preferences.put(FILEPATH_KEY, Constants.DEFAULT_ENGINE_FILEPATH);
    }
}
