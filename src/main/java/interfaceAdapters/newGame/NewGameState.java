package interfaceAdapters.newGame;

import useCase.newGame.NewGameOutputData;

public class NewGameState {
    private String newBoard;
    public NewGameState(String newBoard) {
        this.newBoard = newBoard;
    }
    public String getNewBoard() {
        return newBoard;
    }

}
