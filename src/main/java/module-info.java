module application.homemanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens application.homemanager to javafx.fxml;
    exports application.homemanager;
}