package useCase.movePiece;

import entity.Board;

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
    public void movePiece(MovePieceInputData movePieceInputData) {
        movePieceOutputBoundary.present(movePieceInputData);
    }

    /**
    public void movePiece() {
        //movePieceController.movePiece(move);
        System.out.println("move: " + move);
        String[] moves = move.split(",");
        Integer begin = Integer.parseInt(moves[0]);
        Integer end = Integer.parseInt(moves[1]);
        Pane startPane = (Pane) chessBoard.getChildren().get(begin);
        Pane endPane = (Pane) chessBoard.getChildren().get(end);
        if (startPane.getChildren().size() > 0) {
            ImageView temp = (ImageView) startPane.getChildren().get(0);
            if (endPane.getChildren().size() > 0) {
                endPane.getChildren().remove(0);
            }
            endPane.getChildren().add(temp);
        }
    }
     **/
}
