package useCase.movePiece;

import interfaceAdapters.movePiece.MovePieceViewModel;
import javafx.scene.layout.TilePane;

public interface MovePieceInputBoundary {
    void movePiece(MovePieceInputData movePieceInputData, TilePane chessBoard);
}
