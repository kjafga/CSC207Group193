package useCase.Board;

public class BoardInputData {
    
    private String layout;

    public BoardInputData(String start) {
        this.layout = start;
    }

    public String getPieces() {
        return this.layout;
    }
}
