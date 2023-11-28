package view;

import app.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Objects;

public class MainMenuView implements PropertyChangeListener {

    private final AnchorPane scene;
    public MainMenuView() throws IOException {
        this.scene = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/MainMenu.fxml")));


    }

    public Parent getRoot(){
        return this.scene;
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
