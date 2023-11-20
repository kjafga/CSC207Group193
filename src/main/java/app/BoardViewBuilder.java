package app;

import interfaceAdapters.legalMoves.LegalMovesController;
import interfaceAdapters.legalMoves.LegalMovesViewModel;
import interfaceAdapters.movePiece.MovePieceController;
import interfaceAdapters.movePiece.MovePieceViewModel;
import view.BoardView;

class BoardViewBuilder {

    private LegalMovesViewModel legalMovesViewModel;
    private MovePieceViewModel movePieceViewModel;
    private LegalMovesController legalMovesController;
    private MovePieceController movePieceController;

    BoardViewBuilder setLegalMovesViewModel(LegalMovesViewModel legalMovesViewModel) {
        this.legalMovesViewModel = legalMovesViewModel;
        return this;
    }

    BoardViewBuilder setMovePieceViewModel(MovePieceViewModel movePieceViewModel) {
        this.movePieceViewModel = movePieceViewModel;
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

    BoardView build() {
        if (legalMovesViewModel == null || movePieceViewModel == null ||
                legalMovesController == null || movePieceController == null) {
            throw new IllegalStateException("Did not set all fields!");
        }
        return new BoardView(
                legalMovesViewModel, movePieceViewModel,
                legalMovesController, movePieceController
        );
    }

}
