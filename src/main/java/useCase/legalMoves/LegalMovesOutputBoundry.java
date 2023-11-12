package useCase.legalMoves;

public interface LegalMovesOutputBoundry {

    void prepareSuccessView(LegalMovesOutputData legalMoves);

    void prepareFailView(String error);
}
