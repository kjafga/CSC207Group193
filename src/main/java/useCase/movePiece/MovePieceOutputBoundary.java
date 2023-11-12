package useCase.movePiece;

public interface MovePieceOutputBoundary {

    void prepareSuccessView(MovePieceOutputData move);

    void prepareFailView(MovePieceOutputData move);
}