package useCase.newGame;

import entity.Board;

public class NewGameInteractor implements NewGameInputBoundary {
    private final Board board;
    private final   NewGameOutputBoundary presenter;

    public NewGameInteractor(Board board, NewGameOutputBoundary presenter) {
        this.board = board;
        this.presenter = presenter;
    }

    @Override
    public void execute(NewGameInputData newGameInputData) {

        Integer difficulty = -1;
        if (newGameInputData.difficulty.equals("over the board")) {
            difficulty = -1;
        } else if (newGameInputData.difficulty.equals("easy")) {
            difficulty = 0;
        } else if (newGameInputData.difficulty.equals("medium")) {
            difficulty = 1;
        } else if (newGameInputData.difficulty.equals("hard")) {
            difficulty = 2;
        }
      board.newGame(difficulty, newGameInputData.side);
        presenter.prepareSuccessView(new NewGameOutputData(board.toString().split(" ")[0]));
    }
}
