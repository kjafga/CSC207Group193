package useCase.movePiece;

import entity.Board;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

/**
 *
 * Takes in a pair of ints representing a move and takes that action on the board
 * The interactor will check if a move is valid, and send a fail condition if it is not a valid move
 */

public class MovePieceInteractor implements MovePieceInputBoundary{
    private final MovePieceOutputBoundary movePieceOutputBoundary;
    private final Board board;

    public MovePieceInteractor(MovePieceOutputBoundary movePieceOutputBoundary, Board board) {
        this.movePieceOutputBoundary = movePieceOutputBoundary;
        this.board = board;
    }

    @Override
    public void movePiece(MovePieceInputData movePieceInputData, TilePane chessBoard) {
        movePieceOutputBoundary.present(movePieceInputData);
        int[] moves = movePieceInputData.getMove();
        Pane startPane = (Pane) chessBoard.getChildren().get(moves[0]);
        Pane endPane = (Pane) chessBoard.getChildren().get(moves[1]);
        if (startPane.getChildren().size() > 0) {
            ImageView temp = (ImageView) startPane.getChildren().get(0);
            if (endPane.getChildren().size() > 0) {
                endPane.getChildren().remove(0);
            }
            endPane.getChildren().add(temp);
        }
    }
}
