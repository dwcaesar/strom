module de.wohlers.strom {
    requires javafx.controls;
    requires javafx.fxml;
    requires javax.persistence;
    requires org.slf4j;

    opens de.wohlers.strom to javafx.fxml;
    exports de.wohlers.strom.Views;
}
