package de.wohlers.strom;

import de.wohlers.strom.Lang.Lang;
import javafx.application.Application;
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

    private static Stage      stage;
    public         AnchorPane content;

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
        show("Views/Home.fxml");
    }

    public void showSettings() {
        show("Views/Settings.fxml");
    }

    private void show(String resourceName) {
        try {
            Node child = new FXMLLoader(MainWindow.class.getResource(resourceName), Lang.getBundle()).load();

            content.getChildren().setAll(child);

            AnchorPane.setTopAnchor(child, 0.);
            AnchorPane.setLeftAnchor(child, 0.);
            AnchorPane.setBottomAnchor(child, 0.);
            AnchorPane.setRightAnchor(child, 0.);
        } catch (RuntimeException | IOException e) {
            LOGGER.error("Konnte Scene nicht laden", e);
        }
    }

    public void showMembers() {
        show("Views/Members.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showHome();
    }

}
