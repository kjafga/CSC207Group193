package app;

import interfaceAdapters.ViewManagerModel;
import interfaceAdapters.Board.BoardViewModel;
import interfaceAdapters.legalMoves.LegalMovesController;
import interfaceAdapters.legalMoves.LegalMovesPresenter;
import interfaceAdapters.legalMoves.LegalMovesViewModel;
import interfaceAdapters.movePiece.MovePieceController;
import interfaceAdapters.movePiece.MovePiecePresenter;
import interfaceAdapters.movePiece.MovePieceViewModel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import useCase.legalMoves.LegalMovesInputBoundry;
import useCase.legalMoves.LegalMovesInteractor;
import useCase.legalMoves.LegalMovesOutputBoundry;
import useCase.movePiece.MovePieceInputBoundary;
import useCase.movePiece.MovePieceInputData;
import useCase.movePiece.MovePieceInteractor;
import useCase.movePiece.MovePieceOutputBoundary;

public class Main extends Application {

    String start = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

    ViewManagerModel viewManagerModel = new ViewManagerModel();


    // move piece
    MovePieceViewModel movePieceViewModel = new MovePieceViewModel();
    MovePieceOutputBoundary movePieceOutputBoundary = new MovePiecePresenter(movePieceViewModel);
    MovePieceInputBoundary movePieceInputBoundary = new MovePieceInteractor(movePieceOutputBoundary);
    MovePieceController movePieceController = new MovePieceController(movePieceInputBoundary);

    // legal moves
    LegalMovesViewModel legalMovesViewModel = new LegalMovesViewModel();
    LegalMovesOutputBoundry legalMovesOutputBoundry = new LegalMovesPresenter(legalMovesViewModel);
    LegalMovesInputBoundry legalMovesInputBoundry = new LegalMovesInteractor(legalMovesOutputBoundry);
    LegalMovesController legalMovesController = new LegalMovesController(legalMovesInputBoundry);

    // board
    BoardViewFactory boardViewFactory = new BoardViewFactory();

    BoardViewModel boardViewModel = new BoardViewModel(start);
    BoardView boardView = boardViewFactory.construct(movePieceViewModel, legalMovesViewModel, movePieceController, legalMovesController, boardViewModel);
    
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