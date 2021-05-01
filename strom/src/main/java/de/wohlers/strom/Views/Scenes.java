package de.wohlers.strom.Views;

public enum Scenes {
    MAIN_WINDOW("Window.MainTitle", "MainWindow.fxml");

    private final String windowTitle;
    private final String filename;

    Scenes(String windowTitle, String filename) {
        this.windowTitle = windowTitle;
        this.filename = filename;
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public String getFilename() {
        return filename;
    }
}
