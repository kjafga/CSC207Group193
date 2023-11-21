package interfaceAdapters.movePiece;

import useCase.movePiece.MovePieceInputBoundary;
import useCase.movePiece.MovePieceInputData;

public class MovePieceController {

    private final MovePieceInputBoundary movePieceInputInteractor;

    public MovePieceController(MovePieceInputBoundary movePieceInputInteractor) {
        this.movePieceInputInteractor = movePieceInputInteractor;
    }

    public void execute(int startSquare, int endSquare, char promotion){
        MovePieceInputData inputData = new MovePieceInputData(startSquare, endSquare, promotion);
        movePieceInputInteractor.movePiece(inputData);
    }

}
