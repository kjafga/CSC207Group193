package app;

import interfaceAdapters.Board.BoardViewModel;
import interfaceAdapters.legalMoves.LegalMovesController;
import interfaceAdapters.legalMoves.LegalMovesViewModel;
import interfaceAdapters.movePiece.MovePieceController;
import interfaceAdapters.movePiece.MovePieceViewModel;
import view.BoardView;

public class BoardViewFactory {
    public BoardViewFactory (){

    }

    public BoardView construct(MovePieceViewModel movePieceViewModel, LegalMovesViewModel legalMovesViewModel, MovePieceController movePieceController, LegalMovesController legalMovesController, BoardViewModel boardViewModel){


        BoardView boardView = new BoardView(movePieceViewModel, legalMovesViewModel, movePieceController,legalMovesController, boardViewModel);

        return boardView;
    }
}
