module com.example {
    requires javafx.controls;
    requires javafx.fxml;

//  requires org.controlsfx.controls;
    requires annotations;
    requires java.desktop;
    requires org.json;

    opens app to javafx.fxml;
    exports app;
}
