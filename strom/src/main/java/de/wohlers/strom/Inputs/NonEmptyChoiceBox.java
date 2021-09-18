package de.wohlers.strom.Inputs;

import javafx.beans.Observable;
import javafx.scene.control.ChoiceBox;

public class NonEmptyChoiceBox<T> extends ChoiceBox<T> {

    public NonEmptyChoiceBox() {
        super();
        getSelectionModel().selectedItemProperty().addListener(this::required);
    }

    private void required(Observable o, T oV, T nV) {
        if (nV == null) {
            getStyleClass().add("invalid");
        } else {
            getStyleClass().remove("invalid");
        }
    }

    public void setConverter(RequiredStringConverter<T> converter) {
        super.setConverter(converter);
    }

}
