package useCase.newGame;

public class NewGameOutputData {

    // The only data the presenter needs to prepare the view is the new board.
    private String newBoard;

    public NewGameOutputData(String newBoard) {
        this.newBoard = newBoard;
    }

    public String getNewBoard() {
        return newBoard;
    }
}
