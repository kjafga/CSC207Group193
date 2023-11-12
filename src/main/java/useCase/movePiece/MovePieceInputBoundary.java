package useCase.movePiece;

import interfaceAdapters.movePiece.MovePieceViewModel;

public interface MovePieceInputBoundary {
    void movePiece(MovePieceInputData movePieceInputData, MovePieceViewModel movePieceViewModel);
    
    void execute(MovePieceInputData movePieceInputData);
}
