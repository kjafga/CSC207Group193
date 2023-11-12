package useCase.movePiece;

public class MovePieceInputData {
    int[] move;

    public MovePieceInputData(int[] move){
        this.move = move;
    }

    public int[] getMove() {
        return move;
    }
}
