package app;

import interfaceAdapters.GameOver.GameOverViewModel;
import interfaceAdapters.returnToMainMenu.ReturnToMainMenuController;
import interfaceAdapters.sendBoardToApi.SendBoardToApiController;
import interfaceAdapters.sendBoardToApi.SendBoardToApiViewModel;
import interfaceAdapters.legalMoves.LegalMovesController;
import interfaceAdapters.legalMoves.LegalMovesViewModel;
import interfaceAdapters.movePiece.MovePieceController;
import interfaceAdapters.movePiece.MovePieceViewModel;
import view.BoardView;

class BoardViewBuilder {

    private LegalMovesViewModel legalMovesViewModel;
    private MovePieceViewModel movePieceViewModel;
    private SendBoardToApiViewModel sendBoardToApiViewModel;
    private GameOverViewModel gameOverViewModel;

    private LegalMovesController legalMovesController;
    private MovePieceController movePieceController;
    private SendBoardToApiController sendBoardToApiController;
    private ReturnToMainMenuController returnToMainMenuController;

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

    BoardView build() {
        if (legalMovesViewModel == null || movePieceViewModel == null || sendBoardToApiViewModel == null ||
                legalMovesController == null || movePieceController == null || sendBoardToApiController == null) {
            throw new IllegalStateException("Did not set all fields!");
        }
        return new BoardView(
                legalMovesViewModel, movePieceViewModel, sendBoardToApiViewModel,
                legalMovesController, movePieceController, sendBoardToApiController, gameOverViewModel,
                returnToMainMenuController);
    }

}
