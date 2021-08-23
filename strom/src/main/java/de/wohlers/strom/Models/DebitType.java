package de.wohlers.strom.Models;

import de.wohlers.strom.Lang.Lang;

public enum DebitType {

    DIRECT_DEBIT("DebitType.DirectDebit", true),
    INVOICE("DebitType.Invoice", false);

    final private String  key;
    final private boolean value;

    DebitType(String key, boolean value) {
        this.key = key;
        this.value = value;
    }

    public static DebitType getValue(boolean directDebit) {
        return directDebit ? DIRECT_DEBIT : INVOICE;
    }

    public boolean isValue() {
        return value;
    }

    @Override
    public String toString() {
        return Lang.get(this.key);
    }
}
