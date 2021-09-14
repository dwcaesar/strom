package de.wohlers.strom.Views;

import de.wohlers.strom.DAO.PlaceDAO;
import de.wohlers.strom.Lang.Lang;
import de.wohlers.strom.Models.Place;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Places implements Initializable {

    private final Logger logger = Logger.getLogger(Places.class.getName());

    @FXML
    private       Button                     editButton;
    @FXML
    private       Button                     deleteButton;
    @FXML
    private       TableView<Place>           placeTable;
    @FXML
    private       TableColumn<Place, String> nameColumn;
    private final PlaceDAO                   placeDAO = PlaceDAO.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        placeTable.getSelectionModel().getSelectedItems().addListener(this::onSelectionChange);
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        loadDataAsync();
    }

    private void loadDataAsync() {
        final Service<List<Place>> service = new Service<>() {
            @Override
            protected Task<List<Place>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<Place> call() {
                        return placeDAO.findAll();
                    }
                };
            }
        };
        service.setOnSucceeded(e -> {
            placeTable.getItems().setAll(service.getValue());
            placeTable.getItems().addListener(this::persistChange);
        });
        service.setOnFailed(e -> logger.severe("load data has failed " + service.getException().toString()));
        service.start();
    }

    private void persistChange(ListChangeListener.Change<? extends Place> m) {
        while (m.next()) {
            if (m.wasAdded()) {
                m.getAddedSubList().forEach(placeDAO::persist);
            }
            if (m.wasRemoved()) {
                m.getRemoved().forEach(placeDAO::remove);
            }
        }
    }

    private void onSelectionChange(ListChangeListener.Change<? extends Place> change) {
        if (placeTable.getSelectionModel().isEmpty()) {
            editButton.setDisable(true);
            deleteButton.setDisable(true);
        } else {
            editButton.setDisable(false);
            deleteButton.setDisable(false);
        }
    }

    public void showNewPlaceDialog() {
        Place place = new Place();
        EditPlaceDialog.open(place, this::addPlace, this::deletePlace);
    }

    public void showEditPlaceDialog() {
        Place place = placeTable.getSelectionModel().getSelectedItem();
        EditPlaceDialog.open(place, this::updatePlace, this::deletePlace);
    }

    public void showDeletePlaceDialog() {
        DeleteDialog.open(placeTable.getSelectionModel().getSelectedItem(), this::deletePlace, Lang.get("Dialog.Place.Delete"));
    }

    private void addPlace(Place place) {
        placeTable.getItems().add(place);
    }

    private void updatePlace(Place place) {
        placeDAO.merge(place);
    }

    private void deletePlace(Place place) {
        placeTable.getItems().remove(place);
    }
}
