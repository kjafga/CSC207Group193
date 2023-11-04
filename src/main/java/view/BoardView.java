package view;

import interfaceAdapters.legalMoves.LegalMovesController;
import interfaceAdapters.legalMoves.LegalMovesViewModel;
import interfaceAdapters.movePiece.MovePieceController;
import interfaceAdapters.movePiece.MovePieceViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BoardView extends Application implements ActionListener, PropertyChangeListener {

    // View Models
    private final MovePieceViewModel movePieceViewModel;
    private final LegalMovesViewModel legalMovesViewModel;

    // Controllers
    private final MovePieceController movePieceController;
    private final LegalMovesController legalMovesController;

    public BoardView(MovePieceViewModel movePieceViewModel, LegalMovesViewModel legalMovesViewModel, MovePieceController movePieceController, LegalMovesController legalMovesController) {
        this.movePieceViewModel = movePieceViewModel;
        this.legalMovesViewModel = legalMovesViewModel;
        this.movePieceController = movePieceController;
        this.legalMovesController = legalMovesController;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(View.class.getResource("view.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
