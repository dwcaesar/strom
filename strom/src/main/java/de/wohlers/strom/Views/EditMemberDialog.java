package de.wohlers.strom.Views;

import de.wohlers.strom.DAO.MemberDAO;
import de.wohlers.strom.Lang.Lang;
import de.wohlers.strom.MainWindow;
import de.wohlers.strom.Models.Member;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class EditMemberDialog implements Initializable {
    private final SimpleObjectProperty<Member> member = new SimpleObjectProperty<>();
    @FXML
    private       Stage                        stage;
    @FXML
    private       Button                       deleteButton;
    @FXML
    private       Button                       saveButton;
    @FXML
    private       TextField                    nameField;
    private       Consumer<Member>             onSave;

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
        member.addListener((observable, oldValue, newValue) -> {
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

    public void focusSave() {
        saveButton.requestFocus();
    }
}
