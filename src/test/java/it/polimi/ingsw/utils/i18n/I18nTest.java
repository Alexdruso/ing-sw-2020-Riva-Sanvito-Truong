package it.polimi.ingsw.utils.i18n;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class I18nTest {
    @ParameterizedTest
    @ValueSource(strings = {"", "en", "it"})
    void testStrings(String language) {
        assertNotNull(I18n.getResourceBundle());
        if (!language.equalsIgnoreCase("")) {
            Locale locale = new Locale(language);
            I18n.setLocale(locale);
            assertEquals(locale, I18n.getLocale());
            assertNotNull(I18n.getResourceBundle());
        }
        for (I18nKey key: I18nKey.values()) {
            String s = I18n.string(key);
            assertNotNull(s);
            assertNotEquals("", s);
        }
    }
}