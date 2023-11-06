package app;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.print.DocFlavor.URL;

import interfaceAdapters.ViewManagerModel;
import interfaceAdapters.Board.BoardController;
import interfaceAdapters.Board.BoardPresenter;
import interfaceAdapters.Board.BoardViewModel;
import interfaceAdapters.legalMoves.LegalMovesController;
import interfaceAdapters.legalMoves.LegalMovesViewModel;
import interfaceAdapters.movePiece.MovePieceController;
import interfaceAdapters.movePiece.MovePieceViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import useCase.Board.BoardInputData;
import view.BoardView;

public class Main extends Application {
    
    String start = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

    BoardViewModel boardViewModel = new BoardViewModel(start);
    ViewManagerModel viewManagerModel = new ViewManagerModel();
    MovePieceViewModel movePieceViewModel = new MovePieceViewModel();
    LegalMovesViewModel legalMovesViewModel = new LegalMovesViewModel();
    MovePieceController movePieceController = new MovePieceController();
    LegalMovesController legalMovesController = new LegalMovesController();

    BoardView boardView = new BoardView(movePieceViewModel, legalMovesViewModel, movePieceController, legalMovesController, boardViewModel);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        TilePane view = boardView.chessBoard;

        Scene scene = new Scene(view, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }



}
