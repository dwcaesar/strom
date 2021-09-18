package de.wohlers.strom.Lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Verwaltung von Textbausteinen
 */
public class Lang {

    private static final Logger LOGGER = LoggerFactory.getLogger(Lang.class);

    private static ResourceBundle selectedBundle;

    public static String get(String key, String... variables) {
        getBundle();
        try {
            String val = selectedBundle.getString(key);
            for (int i = 0; i < variables.length; i++) {
                val = val.replace(":" + i + ":", variables[i]);
            }
            return val;
        } catch (MissingResourceException e) {
            LOGGER.info("Textbaustein nicht in ausgewählter Sprache gefunden", e);
        }
        return key;
    }

    private static Locale getLang() {
        // TODO Eventuell die Sprache änderbar machen
        return Locale.getDefault();
    }

    public static ResourceBundle getBundle() {
        if (selectedBundle == null) {
            selectedBundle = ResourceBundle.getBundle("lang", getLang());
        }
        return selectedBundle;
    }

    public static NumberFormat getCurrencyFormat() {
        return NumberFormat.getCurrencyInstance(getLang());
    }
}
