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
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import useCase.legalMoves.LegalMovesInputBoundry;
import useCase.legalMoves.LegalMovesInteractor;
import useCase.legalMoves.LegalMovesOutputBoundry;
import useCase.movePiece.MovePieceInputBoundry;
import useCase.movePiece.MovePieceInputData;
import useCase.movePiece.MovePieceInteractor;
import useCase.movePiece.MovePieceOutputBoundry;
import view.BoardView;

public class Main extends Application {

    String start = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

    BoardViewModel boardViewModel = new BoardViewModel(start);
    ViewManagerModel viewManagerModel = new ViewManagerModel();



    MovePieceViewModel movePieceViewModel = new MovePieceViewModel();

    MovePieceOutputBoundry movePieceOutputBoundry = new MovePiecePresenter(movePieceViewModel);
    MovePieceInputBoundry movePieceInputBoundry = new MovePieceInteractor(movePieceOutputBoundry);

    MovePieceController movePieceController = new MovePieceController(movePieceInputBoundry);


    LegalMovesViewModel legalMovesViewModel = new LegalMovesViewModel();
    LegalMovesOutputBoundry legalMovesOutputBoundry = new LegalMovesPresenter(legalMovesViewModel);
    LegalMovesInputBoundry legalMovesInputBoundry = new LegalMovesInteractor(legalMovesOutputBoundry);
    LegalMovesController legalMovesController = new LegalMovesController(legalMovesInputBoundry);


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