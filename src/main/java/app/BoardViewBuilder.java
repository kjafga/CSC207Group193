package app;

import interfaceAdapters.GameOver.GameOverViewModel;
import interfaceAdapters.book.BookController;
import interfaceAdapters.book.BookViewModel;
import interfaceAdapters.returnToMainMenu.ReturnToMainMenuController;
import interfaceAdapters.sendBoardToApi.SendBoardToApiController;
import interfaceAdapters.sendBoardToApi.SendBoardToApiViewModel;
import interfaceAdapters.legalMoves.LegalMovesController;
import interfaceAdapters.legalMoves.LegalMovesViewModel;
import interfaceAdapters.movePiece.MovePieceController;
import interfaceAdapters.movePiece.MovePieceViewModel;
import view.BoardView;

import java.io.IOException;

class BoardViewBuilder {

    private LegalMovesViewModel legalMovesViewModel;
    private MovePieceViewModel movePieceViewModel;
    private SendBoardToApiViewModel sendBoardToApiViewModel;
    private GameOverViewModel gameOverViewModel;
    private BookViewModel bookViewModel;

    private LegalMovesController legalMovesController;
    private MovePieceController movePieceController;
    private SendBoardToApiController sendBoardToApiController;
    private ReturnToMainMenuController returnToMainMenuController;
    private BookController bookController;

    BoardViewBuilder setLegalMovesViewModel(LegalMovesViewModel legalMovesViewModel) {
        this.legalMovesViewModel = legalMovesViewModel;
        return this;
    }

    BoardViewBuilder setMovePieceViewModel(MovePieceViewModel movePieceViewModel) {
        this.movePieceViewModel = movePieceViewModel;
        return this;
    }

    BoardViewBuilder setSendBoardToApiViewModel(SendBoardToApiViewModel sendBoardToApiViewModel) {
        this.sendBoardToApiViewModel = sendBoardToApiViewModel;
        return this;
    }
    BoardViewBuilder setGameOverViewModel(GameOverViewModel gameOverViewModel) {
        this.gameOverViewModel = gameOverViewModel;
        return this;
    }

    BoardViewBuilder setBookViewModel(BookViewModel bookViewModel) {
        this.bookViewModel = bookViewModel;
        return this;
    }


    BoardViewBuilder setLegalMovesController(LegalMovesController legalMovesController) {
        this.legalMovesController = legalMovesController;
        return this;
    }

    BoardViewBuilder setMovePieceController(MovePieceController movePieceController) {
        this.movePieceController = movePieceController;
        return this;
    }

    BoardViewBuilder setSendBoardToApiController(SendBoardToApiController sendBoardToApiController) {
        this.sendBoardToApiController = sendBoardToApiController;
        return this;
    }
    BoardViewBuilder setReturnToMainMenuController(ReturnToMainMenuController returnToMainMenuController) {
        this.returnToMainMenuController = returnToMainMenuController;
        return this;
    }

    BoardViewBuilder setBookController(BookController bookController) {
        this.bookController = bookController;
        return this;
    }


    BoardView build() throws IOException {
        return new BoardView(legalMovesViewModel, legalMovesController,
                movePieceViewModel, movePieceController,
                sendBoardToApiViewModel, sendBoardToApiController,
                gameOverViewModel,
                returnToMainMenuController,
                bookViewModel, bookController);
    }

}
