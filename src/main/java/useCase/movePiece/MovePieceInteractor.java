package useCase.movePiece;


/**
 *
 * Takes in a string representing a move and takes that action on the board
 * The interactor will check if a move is valid, and send a fail condition if it is not a valid move
 */

public class MovePieceInteractor implements MovePieceInputBoundry{
    private final MovePieceOutputBoundry movePieceOutputBoundry;

    public MovePieceInteractor(MovePieceOutputBoundry movePieceOutputBoundry) {
        this.movePieceOutputBoundry = movePieceOutputBoundry;
    }
}
