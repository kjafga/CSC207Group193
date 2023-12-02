package view;

import app.Main;
import interfaceAdapters.ViewManagerModel;
import interfaceAdapters.newGame.NewGameController;
import interfaceAdapters.newGame.NewGameViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class MainMenuView implements PropertyChangeListener {
    private final String name = "MainMenuView";

    private final NewGameViewModel newGameViewModel;
    private final NewGameController newGameController;

    private final AnchorPane scene;

    public String getViewName(){
        return this.name;
    }

    public MainMenuView( NewGameViewModel newGameViewModel, NewGameController newGameController) throws IOException {

        this.newGameViewModel = newGameViewModel;
        this.newGameController = newGameController;


        this.scene = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/MainMenu.fxml")));
        for (Node node : scene.getChildren()) {
            node.setOnMouseClicked(this::onButtonClicked);
        }

    }




    private void onButtonClicked(MouseEvent e) {
        String source = e.getSource().toString();
        String pattern  = "[^']*'([^']*).*";
        source = source.replaceAll(pattern, "$1");
        if (source.equals("New Game")){
           this.promptForGameType();
        }
    }

    private void promptForGameType() {
        ChoiceDialog<String> difficultyDialog = new ChoiceDialog<>(null, "Easy", "Medium", "Hard", "Over The Table");
        difficultyDialog.setTitle("Pick a Difficulty");
        difficultyDialog.setHeaderText("Pick a Difficulty");
        Optional<String> difficulty = difficultyDialog.showAndWait();

        ChoiceDialog<String> sideDialog = new ChoiceDialog<>(null, "White", "Black","Random");
        sideDialog.setTitle("Pick Your Side");
        sideDialog.setHeaderText("Pick Your Side");
        Optional<String> side = sideDialog.showAndWait();

        this.newGameController.execute(difficulty.get(),side.get());

    }

    public Parent getRoot(){
        return this.scene;
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
