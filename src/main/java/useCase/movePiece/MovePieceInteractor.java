package useCase.movePiece;

import entity.Board;

public class MovePieceInteractor implements MovePieceInputBoundary {

    private final MovePieceOutputBoundary movePieceOutputBoundary;
    private final Board board;

    public MovePieceInteractor(MovePieceOutputBoundary movePieceOutputBoundary, Board board) {
        this.movePieceOutputBoundary = movePieceOutputBoundary;
        this.board = board;
    }

    @Override
    public void movePiece(MovePieceInputData movePieceInputData) {
        final int startSquare = movePieceInputData.startSquare();
        final int endSquare = movePieceInputData.endSquare();
        final char promotion = movePieceInputData.promotion();

        if (board.makeMove(startSquare, endSquare, promotion)) {
            MovePieceOutputData outputData = new MovePieceOutputData(board.toString().split(" ")[0]);
            movePieceOutputBoundary.prepareSuccessView(outputData);
        } else {
            movePieceOutputBoundary.preparePromotionQuestion();
        }
    }

}
