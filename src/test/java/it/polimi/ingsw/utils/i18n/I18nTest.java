package it.polimi.ingsw.utils.i18n;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class I18nTest {
    @ParameterizedTest
    @ValueSource(strings = {"", "en", "it"})
    void testStrings(String language) {
        if (!language.equalsIgnoreCase("")) {
            I18n.setLocale(new Locale(language));
        }
        for (I18nKey key: I18nKey.values()) {
            String s = I18n.string(key);
            assertNotNull(s);
            assertNotEquals(s, "");
        }
    }
}