package it.polimi.ingsw.utils.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Handles the internationalization (i18n) of Santorini.
 */
public class I18n {
    private static final String RESOURCE_BUNDLE_BASE_NAME = "i18n.strings";
    private static ResourceBundle resourceBundle;
    private static Locale locale;
    private static final String LANGUAGE_ENV_VAR_NAME = "LANGUAGE";

    private I18n() {
        throw new IllegalStateException("This class is meant to be used only with static methods");
    }

    /**
     * Initializes the class by making sure an appropriate localized strings resource bundle is loaded.
     */
    private static void init() {
        if (locale == null) {
            String language = System.getenv(LANGUAGE_ENV_VAR_NAME);
            try {
                locale = new Locale(language);
            }
            catch (NullPointerException ignored) {
                locale = Locale.getDefault();
            }
        }
        if (resourceBundle == null) {
            loadResourceBundle();
        }
    }

    /**
     * Sets the locale to use.
     *
     * @param locale the locale to use
     */
    public static void setLocale(Locale locale) {
        I18n.locale = locale;
        loadResourceBundle();
    }

    /**
     * Returns the Locale currently in use.
     * @return the Locale currently in use
     */
    public static Locale getLocale(){
        return I18n.locale;
    }

    /**
     * Forces the loading of the appropriate resource bundle.
     */
    private static void loadResourceBundle() {
        resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME, locale);
    }

    /**
     * Provides the localized string for the given I18nKey.
     * This function takes care of initializing I18n if it isn't already, so it can be called at any time.
     *
     * @param key the key to provide the localized string for
     * @return the localized string
     */
    public static String string(I18nKey key) {
        init();
        return resourceBundle.getString(key.toString().toLowerCase());
    }

    /**
     * Gets the currently active localization resource bundle.
     *
     * @return the currently active localization resource bundle
     */
    public static ResourceBundle getResourceBundle() {
        if (resourceBundle == null) {
            init();
        }
        return resourceBundle;
    }
}
