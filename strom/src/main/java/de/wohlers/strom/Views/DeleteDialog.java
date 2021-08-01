package de.wohlers.strom.Views;

import de.wohlers.strom.Lang.Lang;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class DeleteDialog<T> implements Initializable {

    public  Text        message;
    private T           member;
    private Consumer<T> onDelete;
    private Stage       stage;
    private String messageText;

    public static <E> void open(E member, Consumer<E> onDelete, String messageText) {
        try {
            FXMLLoader      loader     = new FXMLLoader(DeleteDialog.class.getResource("DeleteDialog.fxml"));
            Parent          parent     = loader.load();
            DeleteDialog<E> controller = loader.getController();
            controller.setMember(member);
            controller.setOnDelete(onDelete);
            controller.messageText = messageText;

            Scene scene = new Scene(parent);
            controller.stage = new Stage();
            controller.stage.setTitle(Lang.get("Dialog.Title.Delete"));
            controller.stage.initModality(Modality.APPLICATION_MODAL);
            controller.stage.setScene(scene);
            controller.stage.show();
        } catch (IOException e) {
            LoggerFactory.getLogger(DeleteDialog.class).error("Konnte Dialog nicht öffnen", e);
        }
    }

    private void setMember(T member) {
        this.member = member;
    }

    public void setOnDelete(Consumer<T> onDelete) {
        this.onDelete = onDelete;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        message.setText(messageText);
        // TODO - die Abmaße vom Dialog müssen nachträglich angepasst werden, sodass die Nachricht immer vollständig gezeigt wird
    }

    public void onDelete(ActionEvent actionEvent) {
        onDelete.accept(member);
        stage.close();
    }

    public void onCancel(ActionEvent actionEvent) {
        stage.close();
    }
}
