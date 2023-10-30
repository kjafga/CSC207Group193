module com.example {
    requires javafx.controls;
    requires javafx.fxml;

//  requires org.controlsfx.controls;
    requires annotations;

    opens view to javafx.fxml;
    exports view;
}
