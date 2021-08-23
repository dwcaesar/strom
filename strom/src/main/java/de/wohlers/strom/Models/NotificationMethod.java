package de.wohlers.strom.Models;

import de.wohlers.strom.Lang.Lang;

public enum NotificationMethod {
    EMAIL("NotificationMethod.Email", "email"),
    EPOST("NotificationMethod.Epost", "ePost"),
    MAIL("NotificationMethod.Mail", "mail"),
    IN_PERSON("NotificationMethod.InPerson", "inPerson");

    private final String key;
    private final String value;

    NotificationMethod(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Lang.get(this.key);
    }
}
