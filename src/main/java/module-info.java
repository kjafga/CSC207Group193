module com.example {
    requires javafx.controls;
    requires javafx.fxml;

//  requires org.controlsfx.controls;
    requires annotations;
    requires java.desktop;

    opens view to javafx.fxml;
    exports view;
}
