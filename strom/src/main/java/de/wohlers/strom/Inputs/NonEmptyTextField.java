package de.wohlers.strom.Inputs;

import de.wohlers.strom.Lang.Lang;
import javafx.beans.Observable;
import javafx.scene.control.TextField;

public class NonEmptyTextField extends TextField {

    public NonEmptyTextField() {
        super("");
        textProperty().addListener(this::required);
    }

    private void required(Observable o, String oV, String nV) {
        if (nV == null || nV.isBlank() || nV.isEmpty()) {
            getStyleClass().add("invalid");
            promptTextProperty().set(Lang.get("Generic.RequiredField"));
        } else {
            getStyleClass().remove("invalid");
            promptTextProperty().set(null);
        }
    }
}
