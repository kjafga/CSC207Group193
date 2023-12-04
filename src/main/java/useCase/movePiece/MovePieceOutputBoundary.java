package useCase.movePiece;

public interface MovePieceOutputBoundary {

    void prepareSuccessView(MovePieceOutputData outputData);

    void preparePromotionQuestion();

    void prepareGameOverView(MovePieceOutputData gameOverMessage);

}
