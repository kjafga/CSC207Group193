package useCase.legalMoves;


import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void execute(LegalMovesInputData legalMovesInputData) {
        List<String> legalMoves = new  ArrayList<>();
        legalMoves.add("test");
        legalMovesOutputBoundry.prepareSuccessView(new LegalMovesOutputData(legalMoves));
    }
}
