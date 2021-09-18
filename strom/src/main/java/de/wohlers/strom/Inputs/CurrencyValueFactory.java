package de.wohlers.strom.Inputs;

import de.wohlers.strom.Lang.Lang;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;

import java.text.ParseException;
import java.util.Objects;

public class CurrencyValueFactory extends SpinnerValueFactory<Double> {

    public CurrencyValueFactory() {
        super();
        setConverter(Converter.getInstance());
    }

    @Override
    public void decrement(int steps) {
        setValue(getValue() - steps);
    }

    @Override
    public void increment(int steps) {
        setValue(getValue() + steps);
    }

    private static class Converter extends StringConverter<Double> {

        private static Converter instance;

        public static Converter getInstance() {
            if (instance == null) {
                instance = new Converter();
            }
            return instance;
        }

        @Override
        public String toString(Double object) {
            return Lang.getCurrencyFormat().format(Objects.requireNonNullElse(object, 0.));
        }

        @Override
        public Double fromString(String string) {
            try {
                return Lang.getCurrencyFormat().parse(string).doubleValue();
            } catch (ParseException e) {
                return 0.;
            }
        }
    }
}
