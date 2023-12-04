package app;

import entity.Board;
import interfaceAdapters.GameOver.GameOverPresenter;
import interfaceAdapters.GameOver.GameOverViewModel;
import interfaceAdapters.ViewManagerModel;
import interfaceAdapters.book.BookController;
import interfaceAdapters.book.BookPresenter;
import interfaceAdapters.book.BookViewModel;
import interfaceAdapters.newGame.NewGameController;
import interfaceAdapters.newGame.NewGamePresenter;
import interfaceAdapters.newGame.NewGameViewModel;
import interfaceAdapters.returnToMainMenu.ReturnToMainMenuController;
import interfaceAdapters.returnToMainMenu.ReturnToMainMenuPresenter;
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
import javafx.scene.Scene;
import javafx.stage.Stage;
import useCase.book.BookInputBoundary;
import useCase.book.BookInteractor;
import useCase.book.BookOutputBoundary;
import useCase.gameOver.GameOverOutputBoundary;
import useCase.legalMoves.LegalMovesInputBoundary;
import useCase.legalMoves.LegalMovesInteractor;
import useCase.legalMoves.LegalMovesOutputBoundary;
import useCase.movePiece.MovePieceInputBoundary;
import useCase.movePiece.MovePieceInteractor;
import useCase.movePiece.MovePieceOutputBoundary;
import useCase.newGame.NewGameInputBoundary;
import useCase.newGame.NewGameInteractor;
import useCase.newGame.NewGameOutputBoundary;
import useCase.returnToMainMenu.ReturnToMainMenuInputBoundary;
import useCase.returnToMainMenu.ReturnToMainMenuInteractor;
import useCase.returnToMainMenu.ReturnToMainMenuOutputBoundary;
import useCase.sendBoardToApi.SendBoardToApiInputBoundary;
import useCase.sendBoardToApi.SendBoardToApiInteractor;
import useCase.sendBoardToApi.SendBoardToApiOutputBoundary;
import view.BoardView;
import view.MainMenuView;
import view.ViewManager;

import java.util.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // The One True Board.  Stores the state of the game.
        // Shared by all the controllers.
        Board board = new Board();


        ViewManagerModel viewManagerModel = new ViewManagerModel();

        LegalMovesViewModel legalMovesViewModel = new LegalMovesViewModel();
        LegalMovesOutputBoundary legalMovesOutputBoundary = new LegalMovesPresenter(legalMovesViewModel);
        LegalMovesInputBoundary legalMovesInputBoundary = new LegalMovesInteractor(legalMovesOutputBoundary, board);


        GameOverViewModel gameOverViewModel = new GameOverViewModel();
        GameOverOutputBoundary gameOverOutputBoundry = new GameOverPresenter(gameOverViewModel);


        SendBoardToApiViewModel sendBoardToApiViewModel = new SendBoardToApiViewModel();
        SendBoardToApiOutputBoundary sendBoardToApiOutputBoundary = new SendBoardToApiPresenter(sendBoardToApiViewModel);
        SendBoardToApiInputBoundary sendBoardToApiInputBoundary = new SendBoardToApiInteractor(sendBoardToApiOutputBoundary, gameOverOutputBoundry, board);


        MovePieceViewModel movePieceViewModel = new MovePieceViewModel();
        MovePieceOutputBoundary movePieceOutputBoundary = new MovePiecePresenter(movePieceViewModel);
        MovePieceInputBoundary movePieceInputBoundary = new MovePieceInteractor(movePieceOutputBoundary, gameOverOutputBoundry, sendBoardToApiInputBoundary, board);


        NewGameViewModel newGameViewModel = new NewGameViewModel();
        NewGameOutputBoundary newGameOutputBoundary = new NewGamePresenter(viewManagerModel,newGameViewModel,movePieceViewModel);
        NewGameInputBoundary newGameInteractor = new NewGameInteractor(board, newGameOutputBoundary);
        NewGameController newGameController = new NewGameController(newGameInteractor);

        ReturnToMainMenuOutputBoundary returnToMainMenuOutputBoundary = new ReturnToMainMenuPresenter(viewManagerModel);
        ReturnToMainMenuInputBoundary returnToMainMenuInputBoundary = new ReturnToMainMenuInteractor(returnToMainMenuOutputBoundary);

        BookViewModel bookViewModel = new BookViewModel();
        BookOutputBoundary bookOutputBoundary = new BookPresenter(bookViewModel);
        BookInputBoundary bookInputBoundary = new BookInteractor(bookOutputBoundary, board);

        BoardView boardView = new BoardViewBuilder()
                .setLegalMovesViewModel(legalMovesViewModel)
                .setMovePieceViewModel(movePieceViewModel)
                .setSendBoardToApiViewModel(sendBoardToApiViewModel)
                .setGameOverViewModel(gameOverViewModel)
                .setBookViewModel(bookViewModel)

                .setLegalMovesController(new LegalMovesController(legalMovesInputBoundary))
                .setMovePieceController(new MovePieceController(movePieceInputBoundary))
                .setSendBoardToApiController(new SendBoardToApiController(sendBoardToApiInputBoundary))
                .setReturnToMainMenuController(new ReturnToMainMenuController(returnToMainMenuInputBoundary))
                .setBookController(new BookController(bookInputBoundary))

                .build();



        MainMenuView mainMenuView = new MainMenuView(newGameViewModel, newGameController);

        Scene mainMenuScene = new Scene(mainMenuView.getRoot(),800,800);
        Scene boardScene = new Scene(boardView.getRoot());


        Map<String,Scene> scenes = new HashMap<>();

        scenes.put("MainMenuView", mainMenuScene);
        scenes.put("BoardView",boardScene);

        Stage stage = new Stage();


        ViewManager viewManager = new ViewManager(viewManagerModel,scenes);
        viewManager.start(stage);
    }

}
