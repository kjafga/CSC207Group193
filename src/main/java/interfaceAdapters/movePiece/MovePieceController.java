package interfaceAdapters.movePiece;

import useCase.legalMoves.LegalMovesInputBoundry;
import useCase.legalMoves.LegalMovesInputData;
import useCase.movePiece.MovePieceInputBoundary;
import useCase.movePiece.MovePieceInputData;

public class MovePieceController {

    final MovePieceInputBoundary movePieceInputInteractor;

    public MovePieceController(MovePieceInputBoundary movePieceInputInteractor) {
        this.movePieceInputInteractor = movePieceInputInteractor;
    }

    public void execute(String move){
        MovePieceInputData movePieceInputData = new MovePieceInputData(move);
        movePieceInputInteractor.execute(movePieceInputData);
    }
}
