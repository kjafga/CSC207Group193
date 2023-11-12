package interfaceAdapters.movePiece;

import useCase.legalMoves.LegalMovesInputBoundry;
import useCase.legalMoves.LegalMovesInputData;
import useCase.movePiece.MovePieceInputBoundry;
import useCase.movePiece.MovePieceInputData;

public class MovePieceController {

    final MovePieceInputBoundry movePieceInputInteractor;

    public MovePieceController(MovePieceInputBoundry movePieceInputInteractor) {
        this.movePieceInputInteractor = movePieceInputInteractor;
    }

    public void execute(String move){
        MovePieceInputData movePieceInputData = new MovePieceInputData(move);
        movePieceInputInteractor.execute(movePieceInputData);
    }
}
