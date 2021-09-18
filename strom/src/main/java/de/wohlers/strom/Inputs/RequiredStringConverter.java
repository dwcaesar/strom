package de.wohlers.strom.Inputs;

import de.wohlers.strom.Lang.Lang;
import javafx.util.StringConverter;

abstract class RequiredStringConverter<T> extends StringConverter<T> {

    @Override
    final public String toString(T object) {
        if(object == null) {
            return Lang.get("Generic.RequiredField");
        }
        return nonNullToString(object);
    }

    abstract public String nonNullToString(T object);
}
