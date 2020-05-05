package it.polimi.ingsw.utils.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
    private static ResourceBundle resourceBundle;
    private static Locale locale;
    private static final String LANGUAGE_ENV_VAR_NAME = "LANGUAGE";

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
            resourceBundle = ResourceBundle.getBundle("i18n.strings", locale);
        }
    }

    public static String string(I18nKey key) {
        init();
        return resourceBundle.getString(key.toString().toLowerCase());
    }
}
