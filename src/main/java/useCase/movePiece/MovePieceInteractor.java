package useCase.movePiece;

import entity.Board;
import useCase.gameOver.GameOverOutputBoundary;
import useCase.gameOver.GameOverOutputData;
import useCase.sendBoardToApi.SendBoardToApiInputBoundary;

import java.io.IOException;

public class MovePieceInteractor implements MovePieceInputBoundary {

    private final MovePieceOutputBoundary movePieceOutputBoundary;
    private final GameOverOutputBoundary gameOverOutputBoundry;
    private final SendBoardToApiInputBoundary sendBoardToApiInteractor;
    private final Board board;

    public MovePieceInteractor(MovePieceOutputBoundary movePieceOutputBoundary, GameOverOutputBoundary gameOverOutputBoundry, SendBoardToApiInputBoundary sendBoardToApiInteractor, Board board) {
        this.movePieceOutputBoundary = movePieceOutputBoundary;
        this.gameOverOutputBoundry = gameOverOutputBoundry;
        this.sendBoardToApiInteractor = sendBoardToApiInteractor;
        this.board = board;
    }

    @Override
    public void movePiece(MovePieceInputData movePieceInputData) {
        final int startSquare = movePieceInputData.startSquare();
        final int endSquare = movePieceInputData.endSquare();
        final char promotion = movePieceInputData.promotion();

        if (board.makeMove(startSquare, endSquare, promotion)) {
            if (board.isGameOver()){
                if (board.getDifficulty() == -1) {
                    MovePieceOutputData outputData = new MovePieceOutputData(board.toString().split(" ")[0],false);
                    movePieceOutputBoundary.prepareSuccessView(outputData);
                }else {
                    MovePieceOutputData outputData = new MovePieceOutputData(board.toString().split(" ")[0],true);
                    movePieceOutputBoundary.prepareSuccessView(outputData);

                    // Calls the sendBoardToApiInteractor on a new thread, allowing the initial thread to continue
                    // and return the board to the user. The board after the API call will return when it's ready.
                    Thread sendBoardThread = new Thread(() -> {
                        try {
                            sendBoardToApiInteractor.execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    sendBoardThread.setName("Send Board to API Thread");
                    sendBoardThread.setDaemon(true);
                    sendBoardThread.start();
                }
            }else {
                // The game is over, first update the board, then send an end of game event to the view so the
                // user can see the final board and the reason why the game is over.
                MovePieceOutputData finalBoard = new MovePieceOutputData(board.toString().split(" ")[0],false);
                movePieceOutputBoundary.prepareSuccessView(finalBoard);
                // New boundrary used for the game over case, making the data less messy, and allowing the API interactor
                // to cause the same effect more eaisly.
                GameOverOutputData outputData = new GameOverOutputData(board.getGameOverReason().toString());
                gameOverOutputBoundry.prepareGameOverView(outputData);
            }
        } else {
            movePieceOutputBoundary.preparePromotionQuestion();
        }
    }

}
