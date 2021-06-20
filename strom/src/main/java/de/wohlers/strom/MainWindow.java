package de.wohlers.strom;

import de.wohlers.strom.Lang.Lang;
import de.wohlers.strom.Views.Scenes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ResourceBundle;

import static java.lang.System.exit;

public class MainWindow extends Application {

    static final Logger LOGGER = LoggerFactory.getLogger(MainWindow.class);

    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        MainWindow.stage = stage;

        loadScene(Scenes.MAIN_WINDOW);

        MainWindow.stage.show();
    }

    public static void loadScene(Scenes scene) {
        try {
            stage.setTitle(Lang.get(scene.getWindowTitle()));
            stage.setScene(new Scene(new FXMLLoader(MainWindow.class.getResource(scene.getFilename())).load()));
        } catch (RuntimeException | IOException e) {
            LOGGER.error("Konnte Scene nicht laden", e);
        }
    }
}
