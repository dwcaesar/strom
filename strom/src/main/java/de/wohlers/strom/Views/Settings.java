package de.wohlers.strom.Views;

import de.wohlers.strom.DAO.ClubDAO;
import de.wohlers.strom.DAO.MemberDAO;
import de.wohlers.strom.Inputs.CurrencyValueFactory;
import de.wohlers.strom.Inputs.MemberConverter;
import de.wohlers.strom.Inputs.NonEmptyChoiceBox;
import de.wohlers.strom.Inputs.NonEmptyTextField;
import de.wohlers.strom.Models.Club;
import de.wohlers.strom.Models.Member;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.input.ScrollEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Settings implements Initializable {

    private final Logger logger = Logger.getLogger(Settings.class.getName());

    @FXML
    private NonEmptyTextField         nameField;
    @FXML
    private NonEmptyTextField         streetField;
    @FXML
    private NonEmptyTextField         zipField;
    @FXML
    private NonEmptyTextField         cityField;
    @FXML
    private Spinner<Double>           basicFeeField;
    @FXML
    private Spinner<Double>           connectFeeField;
    @FXML
    private Spinner<Double>           powerRateField;
    @FXML
    private NonEmptyChoiceBox<Member> contactField;
    @FXML
    private NonEmptyChoiceBox<Member> treasurerField;

    private final SimpleObjectProperty<Club> clubProperty = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        basicFeeField.setValueFactory(new CurrencyValueFactory());
        basicFeeField.setOnScroll(this::onScrollSpinner);
        connectFeeField.setValueFactory(new CurrencyValueFactory());
        connectFeeField.setOnScroll(this::onScrollSpinner);
        powerRateField.setValueFactory(new CurrencyValueFactory());
        powerRateField.setOnScroll(this::onScrollSpinner);

        contactField.setConverter(MemberConverter.getInstance());
        treasurerField.setConverter(MemberConverter.getInstance());

        clubProperty.addListener(this::bindInputs);

        loadDataAsync();
    }

    private void bindInputs(ObservableValue<? extends Club> observableValue, Club oldClub, Club club) {
        nameField.textProperty().set(club.getName());
        club.nameProperty().bindBidirectional(nameField.textProperty());
        streetField.textProperty().set(club.getStreet());
        club.streetProperty().bindBidirectional(streetField.textProperty());
        zipField.textProperty().set(club.getZip());
        club.zipProperty().bindBidirectional(zipField.textProperty());
        cityField.textProperty().set(club.getCity());
        club.cityProperty().bindBidirectional(cityField.textProperty());
        basicFeeField.getValueFactory().setValue(club.getBasicFeeRate());
        club.basicFeeRateProperty().bind(basicFeeField.valueProperty());
        connectFeeField.getValueFactory().setValue(club.getConnectFee());
        club.connectFeeProperty().bind(connectFeeField.valueProperty());
        powerRateField.getValueFactory().setValue(club.getPowerRate());
        club.powerRateProperty().bind(powerRateField.valueProperty());
        contactField.getSelectionModel().select(club.getContact());
        contactField.getSelectionModel().selectedItemProperty().addListener((o, oV, nV) -> club.setContact(nV));
        treasurerField.getSelectionModel().select(club.getTreasurer());
        treasurerField.getSelectionModel().selectedItemProperty().addListener((o, oV, nV) -> club.setTreasurer(nV));

        nameField.focusedProperty().addListener(this::persistChanges);
        streetField.focusedProperty().addListener(this::persistChanges);
        zipField.focusedProperty().addListener(this::persistChanges);
        cityField.focusedProperty().addListener(this::persistChanges);
        basicFeeField.focusedProperty().addListener(this::persistChanges);
        connectFeeField.focusedProperty().addListener(this::persistChanges);
        powerRateField.focusedProperty().addListener(this::persistChanges);
        contactField.focusedProperty().addListener(this::persistChanges);
        treasurerField.focusedProperty().addListener(this::persistChanges);
    }

    private void persistChanges(Observable o, Boolean oV, Boolean nV) {
        if(oV && !nV) {
            ClubDAO.getInstance().merge(clubProperty.get());
        }
    }

    private void loadDataAsync() {
        final Service<List<Member>> memberService = new Service<>() {
            @Override
            protected Task<List<Member>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<Member> call() {
                        return MemberDAO.getInstance().findAll();
                    }
                };
            }
        };
        final Service<Club> clubService = new Service<>() {
            @Override
            protected Task<Club> createTask() {
                return new Task<>() {
                    @Override
                    protected Club call() {
                        return ClubDAO.getInstance().findClub();
                    }
                };
            }
        };

        clubService.setOnSucceeded(e -> clubProperty.set(clubService.getValue()));
        clubService.setOnFailed(e -> logger.severe("load data has failed " + clubService.getException().toString()));

        memberService.setOnSucceeded(e -> {
            contactField.getItems().setAll(memberService.getValue());
            treasurerField.getItems().setAll(memberService.getValue());

            clubService.start();
        });

        memberService.start();
    }

    private void onScrollSpinner(ScrollEvent event) {
        Object source = event.getSource();
        if (source instanceof Spinner) {
            if (event.getDeltaY() < 0) {
                ((Spinner<?>) source).decrement();
            } else if (event.getDeltaY() > 0) {
                ((Spinner<?>) source).increment();
            }
        }
    }
}
