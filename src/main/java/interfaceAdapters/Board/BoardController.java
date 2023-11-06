package interfaceAdapters.Board;

import useCase.Board.BoardInputData;
import useCase.Board.BoardOutputData;

public class BoardController {

    private BoardPresenter boardPresenter;

    public BoardController(BoardPresenter boardPresenter) {
        this.boardPresenter = boardPresenter;
    }

    public void prepareBoard(String start) {
        BoardInputData boardInputData = new BoardInputData(start);
        BoardOutputData boardOutputData = new BoardOutputData(boardInputData.getPieces());
        boardPresenter.prepareBoard(boardOutputData);
    }
}
