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
        if (newGameInputData.difficulty.equals("Over The Board")) {
            difficulty = -1;
        } else if (newGameInputData.difficulty.equals("Easy")) {
            difficulty = 0;
        } else if (newGameInputData.difficulty.equals("Medium")) {
            difficulty = 1;
        } else if (newGameInputData.difficulty.equals("Hard")) {
            difficulty = 2;
        }
      board.reset(difficulty, newGameInputData.side);
        presenter.prepareSuccessView(new NewGameOutputData(board.toString().split(" ")[0]));
    }
}
