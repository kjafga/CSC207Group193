package interfaceAdapters.movePiece;

import useCase.movePiece.MovePieceInputBoundary;
import useCase.movePiece.MovePieceInputData;
import useCase.movePiece.MovePieceOutputData;

public class MovePieceController {

    final MovePieceInputBoundary movePieceInputInteractor;

    public MovePieceController(MovePieceInputBoundary movePieceInputInteractor) {
        this.movePieceInputInteractor = movePieceInputInteractor;
    }

    public void execute(int[] move){
        MovePieceInputData movePieceInputData = new MovePieceInputData(move);
        movePieceInputInteractor.movePiece(movePieceInputData);
    }
}
