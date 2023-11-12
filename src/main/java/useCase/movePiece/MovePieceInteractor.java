package useCase.movePiece;

import interfaceAdapters.movePiece.MovePieceViewModel;

/**
 *
 * Takes in a string representing a move and takes that action on the board
 * The interactor will check if a move is valid, and send a fail condition if it is not a valid move
 */

public class MovePieceInteractor implements MovePieceInputBoundary{
    private final MovePieceOutputBoundary movePieceOutputBoundary;

    public MovePieceInteractor(MovePieceOutputBoundary movePieceOutputBoundary) {
        this.movePieceOutputBoundary = movePieceOutputBoundary;
    }

    @Override
    public void movePiece(MovePieceInputData movePieceInputData, MovePieceViewModel movePieceViewModel) {
        movePieceOutputBoundary.present(movePieceInputData, movePieceViewModel);
    }

    public void execute (MovePieceInputData inputData){
        movePieceOutputBoundry.prepareSuccessView(new MovePieceOutputData("test"));
    }
}
