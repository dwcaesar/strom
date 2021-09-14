package de.wohlers.strom.Views;

import de.wohlers.strom.Inputs.NonEmptyTextField;
import de.wohlers.strom.Lang.Lang;
import de.wohlers.strom.MainWindow;
import de.wohlers.strom.Models.Place;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class EditPlaceDialog implements Initializable {

    private final SimpleObjectProperty<Place> place = new SimpleObjectProperty<>();
    @FXML
    private       Stage                       stage;
    @FXML
    private       NonEmptyTextField           nameField;
    @FXML
    private       Button                      deleteButton;
    @FXML
    private       Button                      saveButton;
    private       Consumer<Place>             onSave;
    private       Consumer<Place>             onDelete;

    public static void open(Place place, Consumer<Place> onSave, Consumer<Place> onDelete) {
        try {
            FXMLLoader loader = new FXMLLoader(EditPlaceDialog.class.getResource("EditPlaceDialog.fxml"), Lang.getBundle());
            loader.load();
            EditPlaceDialog controller = loader.getController();
            controller.setPlace(place);
            controller.setOnSave(onSave);
            controller.setOnDelete(onDelete);
        } catch (IOException e) {
            LoggerFactory.getLogger(EditPlaceDialog.class).error("Konnte Dialog nicht öffnen", e);
        }

    }

    private void setPlace(Place place) {
        this.place.set(place);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        place.addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.getId() == null) {
                deleteButton.setDisable(true);
                saveButton.setText(Lang.get("Generic.Button.Create"));
            } else {
                deleteButton.setDisable(false);
                saveButton.setText(Lang.get("Generic.Button.Save"));
            }
            if (newValue != null) {
                nameField.textProperty().set(newValue.getName());
                newValue.nameProperty().bindBidirectional(nameField.textProperty());
            }
        });

        stage.initOwner(MainWindow.getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void setOnSave(Consumer<Place> onSave) {
        this.onSave = onSave;
    }

    public void focusSave() {
        saveButton.requestFocus();
    }

    public void onDelete() {
        DeleteDialog.open(place.get(), p -> {
            onDelete.accept(p);
            stage.close();
        }, Lang.get("Dialog.Place.Delete"));
    }

    public void onCancel() {
        stage.close();
    }

    public void onSave() {
        onSave.accept(place.get());
        stage.close();
    }

    public void setOnDelete(Consumer<Place> onDelete) {
        this.onDelete = onDelete;
    }
}
