package it.polimi.ingsw.utils.i18n;

import java.util.Locale;

/**
 * An enum containing all the available locales for
 */
public enum AvailableLocale {
    ITALIAN("it_IT", "Italiano", Locale.ITALIAN),
    ENGLISH("en_EN", "English", Locale.ENGLISH);

    public final String localeCode;
    public final String languageName;
    public final Locale locale;

    AvailableLocale(String localeCode, String languageName, Locale locale){
        this.localeCode = localeCode;
        this.languageName = languageName;
        this.locale = locale;
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
