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
    public void movePiece(MovePieceInputData movePieceInputData) {
        int selectedSquare = movePieceInputData.move[0];
        int targetSquare = movePieceInputData.move[1];
        MovePieceOutputData movePieceOutputData = new MovePieceOutputData();



        if (board.makeMove(selectedSquare, targetSquare,'?')){
            movePieceOutputData.newBoard = (board.toString().split(" ")[0]);
            movePieceOutputBoundary.prepareSuccessView(movePieceOutputData);
        }
        else{
            movePieceOutputData.newBoard = (board.toString().split(" ")[0]);
            movePieceOutputBoundary.prepareFailView(movePieceOutputData);
        }


    }
