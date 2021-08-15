package de.wohlers.strom.Views;

import de.wohlers.strom.Lang.Lang;
import de.wohlers.strom.MainWindow;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class DeleteDialog<T> implements Initializable {

    @FXML
    private       Stage          stage;
    @FXML
    private       Text           message;
    @FXML
    private       Button         cancel;
    private       T              member;
    private       Consumer<T>    onDelete;
    private final StringProperty messageText = new SimpleStringProperty();

    public static <E> void open(E member, Consumer<E> onDelete, String messageText) {
        try {
            FXMLLoader loader = new FXMLLoader(DeleteDialog.class.getResource("DeleteDialog.fxml"), Lang.getBundle());
            loader.load();
            DeleteDialog<E> controller = loader.getController();
            controller.setMember(member);
            controller.setOnDelete(onDelete);
            controller.messageText.set(messageText);
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
        messageText.addListener((e, o, n) -> message.setText(n));
        stage.initOwner(MainWindow.getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onDelete() {
        onDelete.accept(member);
        stage.close();
    }

    public void onCancel() {
        stage.close();
    }

    public void focusCancel() {
        cancel.requestFocus();
    }
}
