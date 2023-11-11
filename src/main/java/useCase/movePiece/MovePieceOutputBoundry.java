package useCase.movePiece;

import useCase.legalMoves.LegalMovesOutputData;

public interface MovePieceOutputBoundry {

    void prepareSuccessView(MovePieceOutputData move);

    void prepareFailView(String error);
}
