package useCase.legalMoves;


import entity.Board;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Takes in a coordinate on the board and returns a list of legal moves
 *
 */


public class LegalMovesInteractor implements LegalMovesInputBoundry{
    final LegalMovesOutputBoundry legalMovesOutputBoundry;
    final private Board board;

    public LegalMovesInteractor(LegalMovesOutputBoundry legalMovesOutputBoundry, Board board) {
        this.legalMovesOutputBoundry = legalMovesOutputBoundry;
        this.board = board;
    }

    @Override
    public void execute(LegalMovesInputData legalMovesInputData) {
        List<Integer> legalMoves = board.getLegalMoves(legalMovesInputData.position);
        legalMovesOutputBoundry.prepareSuccessView(new LegalMovesOutputData(legalMoves));
    }
}
