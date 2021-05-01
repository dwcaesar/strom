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

public class Main extends Application {

    static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;

        loadScene(Scenes.MAIN_WINDOW);

        Main.stage.show();
    }

    public static void loadScene(Scenes scene) {
        try {
            stage.setTitle(Lang.get(scene.getWindowTitle()));
            stage.setScene(new Scene(new FXMLLoader(Main.class.getResource(scene.getFilename())).load()));
        } catch (IOException e) {
            LOGGER.error("Konnte Scene nicht laden", e);
        }
    }
}
