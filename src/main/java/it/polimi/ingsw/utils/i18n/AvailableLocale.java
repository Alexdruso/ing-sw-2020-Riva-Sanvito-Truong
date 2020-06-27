package it.polimi.ingsw.utils.i18n;

import javafx.scene.image.Image;

import java.util.Locale;

/**
 * An enum containing all the available locales for
 */
public enum AvailableLocale {
    ITALIAN("it_IT", "Italiano", Locale.ITALIAN, "/assets/flags/it-flag.png"),
    ENGLISH("en_EN", "English", Locale.ENGLISH, "/assets/flags/uk-us-flag.png");

    public final String localeCode;
    public final String languageName;
    public final Locale locale;
    public final Image icon;

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

    public static AvailableLocale fromLocale(Locale locale){
        return switch(locale.getLanguage()){
            case "it" -> ITALIAN;
            case "en" -> ENGLISH;
            default -> throw new IllegalStateException("Unexpected value: " + locale.getLanguage());
        };
    }
}
