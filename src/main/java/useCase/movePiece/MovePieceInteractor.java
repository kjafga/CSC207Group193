package useCase.movePiece;


/**
 *
 * Takes in a string representing a move and takes that action on the board
 *
 */

public class MovePieceInteractor implements MovePieceInputBoundry{
    private final MovePieceOutputBoundry movePieceOutputBoundry;

    public MovePieceInteractor(MovePieceOutputBoundry movePieceOutputBoundry) {
        this.movePieceOutputBoundry = movePieceOutputBoundry;
    }
}
