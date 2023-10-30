package useCase.legalMoves;


/**
 *
 * Takes in a coordinate on the board and returns a list of legal moves
 *
 */


public class LegalMovesInteractor implements LegalMovesInputBoundry{
    final LegalMovesOutputBoundry legalMovesOutputBoundry;

    public LegalMovesInteractor(LegalMovesOutputBoundry legalMovesOutputBoundry) {
        this.legalMovesOutputBoundry = legalMovesOutputBoundry;
    }
}
