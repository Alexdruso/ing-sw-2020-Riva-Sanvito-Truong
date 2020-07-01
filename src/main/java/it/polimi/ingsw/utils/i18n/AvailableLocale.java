package it.polimi.ingsw.utils.i18n;

import javafx.scene.image.Image;

import java.util.Locale;

/**
 * An enum containing all the available locales for the application.
 */
public enum AvailableLocale {
    /**
     * This element represents the Italian locale
     */
    ITALIAN("it_IT", "Italiano", Locale.ITALIAN, "/assets/flags/it-flag.png"),
    /**
     * This element represents the English locale
     */
    ENGLISH("en_EN", "English", Locale.ENGLISH, "/assets/flags/uk-us-flag.png");

    /**
     * The ISO code for the locale
     */
    public final String localeCode;
    /**
     * The language name as written in the language itself
     */
    public final String languageName;
    /**
     * The Locale enum value for the language
     */
    public final Locale locale;
    /**
     * The icon to be used as a button graphic
     */
    public final Image icon;

    /**
     * The class constructor
     * @param localeCode the ISO code for the locale
     * @param languageName the language name as written in the language itself
     * @param locale the Locale enum value for the language
     * @param icon the Icon to be used as a button graphic
     */
    AvailableLocale(String localeCode, String languageName, Locale locale, String icon){
        this.localeCode = localeCode;
        this.languageName = languageName;
        this.locale = locale;
        this.icon = new Image(icon, 70, 50, false, true);
    }

    @Override
    public String toString(){
        return languageName;
    }

    /**
     * This method is used to retrieve an enum entry from a given Locale
     * @param locale the Locale from which to retrieve the AvailableLocale entry
     * @return the AvailableLocale instance
     */
    public static AvailableLocale fromLocale(Locale locale){
        return switch(locale.getLanguage()){
            case "it" -> ITALIAN;
            case "en" -> ENGLISH;
            default -> throw new IllegalStateException("Unexpected value: " + locale.getLanguage());
        };
    }
}
