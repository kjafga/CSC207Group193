package app;

import entity.Board;
import interfaceAdapters.ViewManagerModel;
import interfaceAdapters.sendBoardToApi.SendBoardToApiController;
import interfaceAdapters.sendBoardToApi.SendBoardToApiPresenter;
import interfaceAdapters.sendBoardToApi.SendBoardToApiViewModel;
import interfaceAdapters.legalMoves.LegalMovesController;
import interfaceAdapters.legalMoves.LegalMovesPresenter;
import interfaceAdapters.legalMoves.LegalMovesViewModel;
import interfaceAdapters.movePiece.MovePieceController;
import interfaceAdapters.movePiece.MovePiecePresenter;
import interfaceAdapters.movePiece.MovePieceViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import useCase.legalMoves.LegalMovesInputBoundary;
import useCase.legalMoves.LegalMovesInteractor;
import useCase.legalMoves.LegalMovesOutputBoundary;
import useCase.movePiece.MovePieceInputBoundary;
import useCase.movePiece.MovePieceInteractor;
import useCase.movePiece.MovePieceOutputBoundary;
import useCase.sendBoardToApi.SendBoardToApiInputBoundary;
import useCase.sendBoardToApi.SendBoardToApiInteractor;
import useCase.sendBoardToApi.SendBoardToApiOutputBoundary;
import view.BoardView;
import view.MainMenuView;
import view.ViewManager;

import java.io.IOException;
import java.util.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // The One True Board.  Stores the state of the game.
        // Shared by all the controllers.
        Board board = new Board();

        LegalMovesViewModel legalMovesViewModel = new LegalMovesViewModel();
        LegalMovesOutputBoundary legalMovesOutputBoundary = new LegalMovesPresenter(legalMovesViewModel);
        LegalMovesInputBoundary legalMovesInputBoundary = new LegalMovesInteractor(legalMovesOutputBoundary, board);

        MovePieceViewModel movePieceViewModel = new MovePieceViewModel();
        MovePieceOutputBoundary movePieceOutputBoundary = new MovePiecePresenter(movePieceViewModel);
        MovePieceInputBoundary movePieceInputBoundary = new MovePieceInteractor(movePieceOutputBoundary, board);

        SendBoardToApiViewModel sendBoardToApiViewModel = new SendBoardToApiViewModel();
        SendBoardToApiOutputBoundary sendBoardToApiOutputBoundary = new SendBoardToApiPresenter(sendBoardToApiViewModel);
        SendBoardToApiInputBoundary sendBoardToApiInputBoundary = new SendBoardToApiInteractor(sendBoardToApiOutputBoundary, board);

        BoardView boardView = new BoardViewBuilder()
                .setLegalMovesViewModel(legalMovesViewModel)
                .setMovePieceViewModel(movePieceViewModel)
                .setSendBoardToApiViewModel(sendBoardToApiViewModel)

                .setLegalMovesController(new LegalMovesController(legalMovesInputBoundary))
                .setMovePieceController(new MovePieceController(movePieceInputBoundary))
                .setSendBoardToApiController(new SendBoardToApiController(sendBoardToApiInputBoundary))

                .build();



        MainMenuView mainMenuView = new MainMenuView();

        Scene mainMenuScene = new Scene(mainMenuView.getRoot(),800,800);
        Scene boardScene = new Scene(boardView.getRoot(), 800, 800);


        Map<String,Scene> scenes = new HashMap<>();

        scenes.put("MainMenuView", mainMenuScene);
        scenes.put("BoardView",boardScene);

        Stage stage = new Stage();

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        ViewManager viewManager = new ViewManager(viewManagerModel,scenes);
        viewManager.start(stage);
    }

}
