package app;

import entity.Board;
import interfaceAdapters.legalMoves.LegalMovesController;
import interfaceAdapters.legalMoves.LegalMovesPresenter;
import interfaceAdapters.legalMoves.LegalMovesViewModel;
import interfaceAdapters.movePiece.MovePieceController;
import interfaceAdapters.movePiece.MovePiecePresenter;
import interfaceAdapters.movePiece.MovePieceViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import useCase.legalMoves.LegalMovesInputBoundary;
import useCase.legalMoves.LegalMovesInteractor;
import useCase.legalMoves.LegalMovesOutputBoundary;
import useCase.movePiece.MovePieceInputBoundary;
import useCase.movePiece.MovePieceInteractor;
import useCase.movePiece.MovePieceOutputBoundary;
import view.BoardView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // The One True Board.  Stores the state of the game.
        // Shared by all the controllers.
        Board board = new Board();

        LegalMovesOutputBoundary legalMovesOutputBoundary = new LegalMovesPresenter(new LegalMovesViewModel());
        LegalMovesInputBoundary legalMovesInputBoundary = new LegalMovesInteractor(legalMovesOutputBoundary, board);

        MovePieceOutputBoundary movePieceOutputBoundary = new MovePiecePresenter(new MovePieceViewModel());
        MovePieceInputBoundary movePieceInputBoundary = new MovePieceInteractor(movePieceOutputBoundary, board);

        BoardView boardView = new BoardViewBuilder()
                .setLegalMovesViewModel(new LegalMovesViewModel())
                .setMovePieceViewModel(new MovePieceViewModel())
                .setLegalMovesController(new LegalMovesController(legalMovesInputBoundary))
                .setMovePieceController(new MovePieceController(movePieceInputBoundary))
                .build();

        Scene scene = new Scene(boardView.getRoot(), 800, 800);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
