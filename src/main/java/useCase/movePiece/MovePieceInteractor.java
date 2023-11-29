package useCase.movePiece;

import entity.Board;
import useCase.sendBoardToApi.SendBoardToApiInputBoundary;
import useCase.sendBoardToApi.SendBoardToApiInteractor;

import java.io.IOException;

public class MovePieceInteractor implements MovePieceInputBoundary {

    private final MovePieceOutputBoundary movePieceOutputBoundary;
    private final SendBoardToApiInputBoundary sendBoardToApiInteractor;
    private final Board board;

    public MovePieceInteractor(MovePieceOutputBoundary movePieceOutputBoundary, SendBoardToApiInputBoundary sendBoardToApiInteractor, Board board) {
        this.movePieceOutputBoundary = movePieceOutputBoundary;
        this.sendBoardToApiInteractor = sendBoardToApiInteractor;
        this.board = board;
    }

    @Override
    public void movePiece(MovePieceInputData movePieceInputData) {
        final int startSquare = movePieceInputData.startSquare();
        final int endSquare = movePieceInputData.endSquare();
        final char promotion = movePieceInputData.promotion();

        if (board.makeMove(startSquare, endSquare, promotion)) {
            if (board.canMove()){


                if (board.getDifficulty() == -1) {

                    MovePieceOutputData outputData = new MovePieceOutputData(board.toString().split(" ")[0],false);
                    movePieceOutputBoundary.prepareSuccessView(outputData);
                }else {
                    MovePieceOutputData outputData = new MovePieceOutputData(board.toString().split(" ")[0],true);
                    movePieceOutputBoundary.prepareSuccessView(outputData);

                    try {
                        sendBoardToApiInteractor.execute();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }else {
            }
        } else {
            movePieceOutputBoundary.preparePromotionQuestion();
        }
    }

}
