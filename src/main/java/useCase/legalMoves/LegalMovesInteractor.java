package useCase.legalMoves;

import entity.Board;

import java.util.List;

/**
 * Takes in a coordinate on the board and returns a list of legal moves
 */
public class LegalMovesInteractor implements LegalMovesInputBoundary {

    private final LegalMovesOutputBoundary legalMovesOutputBoundary;
    private final Board board;

    public LegalMovesInteractor(LegalMovesOutputBoundary legalMovesOutputBoundary, Board board) {
        this.legalMovesOutputBoundary = legalMovesOutputBoundary;
        this.board = board;
    }

    @Override
    public void execute(LegalMovesInputData legalMovesInputData) {
        List<Integer> legalMoves = board.getLegalMoves(legalMovesInputData.startSquare());
        legalMovesOutputBoundary.prepareSuccessView(new LegalMovesOutputData(legalMoves));
    }

}
