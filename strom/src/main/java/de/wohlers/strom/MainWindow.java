package de.wohlers.strom;

import de.wohlers.strom.Lang.Lang;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow extends Application implements Initializable {

    static final Logger LOGGER = LoggerFactory.getLogger(MainWindow.class);

    private static Stage stage;
    public AnchorPane content;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        MainWindow.stage = stage;

        try {
            stage.setTitle(Lang.get("Window.MainTitle"));
            stage.setScene(new Scene(new FXMLLoader(MainWindow.class.getResource("MainWindow.fxml"), Lang.getBundle()).load()));
        } catch (RuntimeException | IOException e) {
            LOGGER.error("Konnte Scene nicht laden", e);
        }

        MainWindow.stage.show();
    }

    public void showHome() {
        try {
            content.getChildren().setAll((Node) new FXMLLoader(MainWindow.class.getResource("Views/Home.fxml"), Lang.getBundle()).load());
        } catch (RuntimeException | IOException exception) {
            LOGGER.error("Konnte Scene nicht laden", exception);
        }
    }

    public void showSettings() {
        try {
            content.getChildren().setAll((Node) new FXMLLoader(MainWindow.class.getResource("Views/Settings.fxml"), Lang.getBundle()).load());
        } catch (RuntimeException | IOException e) {
            LOGGER.error("Konnte Scene nicht laden", e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showHome();
    }

}
