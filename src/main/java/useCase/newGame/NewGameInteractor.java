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

    }
}
