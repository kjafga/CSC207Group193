package interfaceAdapters.Board;

import interfaceAdapters.ViewManagerModel;
import useCase.Board.BoardOutputData;

public class BoardPresenter {

    private final BoardViewModel boardViewModel;
    private ViewManagerModel viewManagerModel;

    public BoardPresenter(BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;
    }

    public void prepareBoard(BoardOutputData boardOutputData) {
        boardViewModel.setPieces(boardOutputData.getPieces());
        boardViewModel.setPieceImages(boardOutputData.getPieceImages());
    }

}
