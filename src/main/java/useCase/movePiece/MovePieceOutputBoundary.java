package useCase.movePiece;

import interfaceAdapters.movePiece.MovePieceViewModel;

public interface MovePieceOutputBoundary {
    void present(MovePieceInputData movePieceInputData, MovePieceViewModel movePieceViewModel);
}
