module com.example.ocvfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires opencv;

    opens com.example.ocvfx to javafx.fxml;
    exports com.example.ocvfx;
    exports ocv;
    opens ocv to javafx.fxml;
}