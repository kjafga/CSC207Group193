package useCase.movePiece;

import interfaceAdapters.movePiece.MovePieceViewModel;

public interface MovePieceOutputBoundary {
    void present(MovePieceInputData movePieceInputData);

    void prepareSuccessView(MovePieceOutputData move);

    void prepareFailView(String error);
}