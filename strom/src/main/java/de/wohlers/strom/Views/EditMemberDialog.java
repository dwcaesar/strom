package de.wohlers.strom.Views;

import de.wohlers.strom.DAO.MemberDAO;
import de.wohlers.strom.Inputs.NonEmptyTextField;
import de.wohlers.strom.Lang.Lang;
import de.wohlers.strom.MainWindow;
import de.wohlers.strom.Models.DebitType;
import de.wohlers.strom.Models.Member;
import de.wohlers.strom.Models.NotificationMethod;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class EditMemberDialog implements Initializable {
    private final SimpleObjectProperty<Member>  member = new SimpleObjectProperty<>();
    @FXML
    private       Stage                         stage;
    @FXML
    private       Button                        deleteButton;
    @FXML
    private       Button                        saveButton;
    @FXML
    private       TextField                     titleField;
    @FXML
    private       NonEmptyTextField             nameField;
    @FXML
    private       NonEmptyTextField             streetField;
    @FXML
    private       NonEmptyTextField             zipField;
    @FXML
    private       NonEmptyTextField             cityField;
    @FXML
    private       NonEmptyTextField             phoneField;
    @FXML
    private       TextField                     emailField;
    @FXML
    private       TextField                     epostField;
    @FXML
    private       ChoiceBox<DebitType>          directDebitField;
    @FXML
    private       ChoiceBox<NotificationMethod> preferredNotificationMethodField;
    private       Consumer<Member>              onSave;

    public static void open(Member member, Consumer<Member> onSave) {
        try {
            FXMLLoader loader = new FXMLLoader(EditMemberDialog.class.getResource("EditMemberDialog.fxml"), Lang.getBundle());
            loader.load();
            EditMemberDialog controller = loader.getController();
            controller.setMember(member);
            controller.setOnSave(onSave);
        } catch (IOException e) {
            LoggerFactory.getLogger(EditMemberDialog.class).error("Konnte Dialog nicht öffnen", e);
        }
    }

    private void setMember(Member member) {
        this.member.set(member);
    }

    private void setOnSave(Consumer<Member> consumer) {
        this.onSave = consumer;
    }

    public void onDelete() {
        DeleteDialog.open(member.get(), MemberDAO.getInstance()::remove, Lang.get("Dialog.Member.Delete"));
        stage.close();
    }

    public void onCancel() {
        stage.close();
    }

    public void onSave() {
        onSave.accept(member.get());
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        directDebitField.getItems().setAll(DebitType.values());
        preferredNotificationMethodField.getItems().setAll(NotificationMethod.values());

        member.addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.getId() == null) {
                deleteButton.setDisable(true);
                saveButton.setText(Lang.get("Generic.Button.Create"));
            } else {
                deleteButton.setDisable(false);
                saveButton.setText(Lang.get("Generic.Button.Save"));
            }
            if (newValue != null) {
                titleField.textProperty().set(newValue.getTitle());
                newValue.titleProperty().bindBidirectional(titleField.textProperty());
                nameField.textProperty().set(newValue.getName());
                newValue.nameProperty().bindBidirectional(nameField.textProperty());
                streetField.textProperty().set(newValue.getStreet());
                newValue.streetProperty().bindBidirectional(streetField.textProperty());
                zipField.textProperty().set(newValue.getZip());
                newValue.zipProperty().bindBidirectional(zipField.textProperty());
                cityField.textProperty().set(newValue.getCity());
                newValue.cityProperty().bindBidirectional(cityField.textProperty());
                phoneField.textProperty().set(newValue.getPhone());
                newValue.phoneProperty().bindBidirectional(phoneField.textProperty());
                emailField.textProperty().set(newValue.getEmail());
                newValue.emailProperty().bindBidirectional(emailField.textProperty());
                epostField.textProperty().set(newValue.getEpost());
                newValue.epostProperty().bindBidirectional(epostField.textProperty());
                directDebitField.getSelectionModel().select(DebitType.getValue(newValue.isDirectDebit()));
                directDebitField.getSelectionModel().selectedItemProperty().addListener((o, oV, nV) -> newValue.setDirectDebit(nV.isValue()));
                preferredNotificationMethodField.getSelectionModel().select(newValue.getPreferredNotificationMethod());
                preferredNotificationMethodField.getSelectionModel().selectedItemProperty().addListener((o, oV, nV) -> newValue.setPreferredNotificationMethod(nV));

                emailField.textProperty().addListener(this::validEmailRequired);
                epostField.textProperty().addListener(this::validEmailRequired);
                preferredNotificationMethodField.getSelectionModel().selectedItemProperty().addListener(this::validEmailRequired);
                validEmailRequired(null, null, null);
            }
        });


        stage.initOwner(MainWindow.getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    private void validEmailRequired(Observable o, Object oV, Object nV) {
        emailField.getStyleClass().remove("invalid");
        emailField.promptTextProperty().setValue(null);
        epostField.getStyleClass().remove("invalid");
        epostField.promptTextProperty().setValue(null);
        String email = emailField.textProperty().getValue();
        String epost = epostField.textProperty().getValue();
        switch (preferredNotificationMethodField.getSelectionModel().getSelectedItem()) {
            case EMAIL:
                if (email == null || email.isBlank() || email.isEmpty()) {
                    emailField.getStyleClass().add("invalid");
                    emailField.promptTextProperty().setValue(Lang.get("Generic.RequiredField"));
                    emailField.textProperty().setValue("");
                }
                break;
            case EPOST:
                if (epost == null || epost.isBlank() || epost.isEmpty()) {
                    epostField.getStyleClass().add("invalid");
                    epostField.promptTextProperty().setValue(Lang.get("Generic.RequiredField"));
                    epostField.textProperty().setValue("");
                }
                break;
        }
        if (email != null && !email.isEmpty() && !email.matches("^[^\\s@]+@\\w+\\.\\w+$")) {
            emailField.getStyleClass().add("invalid");
        }
        if (epost != null && !epost.isEmpty() && !epost.matches("^[^\\s@]+@\\w+\\.\\w+$")) {
            epostField.getStyleClass().add("invalid");
        }
    }

    public void focusSave() {
        saveButton.requestFocus();
    }
}
