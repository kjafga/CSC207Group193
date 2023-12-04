package view;

import interfaceAdapters.ViewManagerModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;

public class ViewManager extends Application implements PropertyChangeListener {

    private final ViewManagerModel viewManagerModel;
    private final Map<String,Scene> scenes;
    private Scene currentScene;
    private  Stage primaryStage = null;
    
    public ViewManager( ViewManagerModel viewManagerModel, Map<String,Scene> scenes) {

        this.viewManagerModel = viewManagerModel;
        this.scenes = scenes;
        this.currentScene = scenes.get("MainMenuView");

        this.viewManagerModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("view")) {
            String sceneName = (String) evt.getNewValue();
            this.currentScene = scenes.get(sceneName);
            primaryStage.setScene(currentScene);
            primaryStage.show();

        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        primaryStage.setScene(currentScene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
