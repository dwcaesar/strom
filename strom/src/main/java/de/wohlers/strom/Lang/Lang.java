package de.wohlers.strom.Lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Verwaltung von Textbausteinen
 */
public class Lang {

    private static final Logger LOGGER = LoggerFactory.getLogger(Lang.class);

    private static ResourceBundle selectedBundle;
    private static ResourceBundle fallbackBundle;

    public static String get(String key, String... variables) {
        if (selectedBundle == null) {
            selectedBundle = ResourceBundle.getBundle("lang", getLang());
        }
        try {
            String val = selectedBundle.getString(key);
            for (int i = 0; i < variables.length; i++) {
                val = val.replace(":" + i + ":", variables[i]);
            }
            return val;
        } catch (MissingResourceException e) {
            LOGGER.info("Textbaustein nicht in ausgewählter Sprache gefunden", e);
            return getFromFallbackBundle(key, variables);
        }
    }

    private static String getFromFallbackBundle(String key, String... variables) {
        if (fallbackBundle == null) {
            fallbackBundle = ResourceBundle.getBundle("resources/lang", getFallback());
        }
        try {
            String val = fallbackBundle.getString(key);
            for (int i = 0; i < variables.length; i++) {
                val = val.replace(":" + i + ":", variables[i]);
            }
            return val;
        } catch (MissingResourceException e) {
            LOGGER.warn("Textbaustein nicht gefunden", e);
            return key;
        }
    }

    private static Locale getLang() {
        // TODO Eventuell die Sprache änderbar machen
        return Locale.getDefault();
    }

    private static Locale getFallback() {
        return Locale.GERMAN;
    }

}
