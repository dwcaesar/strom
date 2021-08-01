package de.wohlers.strom.Views;

import de.wohlers.strom.DAO.MemberDAO;
import de.wohlers.strom.Lang.Lang;
import de.wohlers.strom.Models.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class EditMemberDialog implements Initializable {
    public  Button           deleteButton;
    public  Button           saveButton;
    private Member           member;
    private Consumer<Member> onSave;
    private Stage            stage;

    public static void open(Member member, Consumer<Member> onSave) {
        try {
            FXMLLoader       loader     = new FXMLLoader(EditMemberDialog.class.getResource("EditMemberDialog.fxml"));
            Parent           parent     = loader.load();
            EditMemberDialog controller = loader.getController();
            controller.setMember(member);
            controller.setOnSave(onSave);

            Scene scene = new Scene(parent);
            controller.stage = new Stage();
            controller.stage.setTitle(Lang.get("Dialog.Title.Edit", member.getName()));
            controller.stage.initModality(Modality.APPLICATION_MODAL);
            controller.stage.setScene(scene);
            controller.stage.show();
        } catch (IOException e) {
            LoggerFactory.getLogger(EditMemberDialog.class).error("Konnte Dialog nicht öffnen", e);
        }
    }

    private void setMember(Member member) {
        this.member = member;
    }

    private void setOnSave(Consumer<Member> consumer) {
        this.onSave = consumer;
    }

    public void onDelete(ActionEvent actionEvent) {
        DeleteDialog.open(member, MemberDAO.getInstance()::remove, Lang.get("Dialog.Title.Delete"));
        // TODO - Löscht das auch aus der TableView?
        MemberDAO.getInstance().remove(member);
        stage.close();
    }

    public void onCancel(ActionEvent actionEvent) {
        // TODO - alten Zustand aus der Datenbank abrufen
        stage.close();
    }

    public void onSave(ActionEvent actionEvent) {
        // TODO - Vorher Werte aus der UI in dem Member ablegen
        onSave.accept(member);
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (member.getId() == 0) {
            deleteButton.setDisable(true);
            saveButton.setText(Lang.get("Button.Generic.Create"));
        }

    }
}
