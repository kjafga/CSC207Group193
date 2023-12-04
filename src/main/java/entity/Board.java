package entity;

import java.util.List;

public class Board {

    private final BoardData boardData;
    private final FENGenerator fenGenerator;
    private final MoveGenerator moveGenerator;
    //Difficulty of the game. -1 means over the board, 0 means easy, 1 means medium, 2 means hard
    private Integer difficulty = -1;

    public Integer getDifficulty() {
        return this.difficulty;
    }

    public void reset(int difficulty, String side) {
        this.difficulty = difficulty;

    }

    public Board() {
        this.boardData = new BoardData();
        this.fenGenerator = new FENGenerator(boardData);
        this.moveGenerator = new MoveGenerator(boardData);
    }

    Board(String fen) {
        this.boardData = FENGenerator.fromFen(fen);
        this.fenGenerator = new FENGenerator(boardData);
        this.moveGenerator = new MoveGenerator(boardData);
    }

    /**
     * @return The position described in Forsyth&ndash;Edwards Notation (FEN).
     */
    @Override
    public String toString() {
        return fenGenerator.toString();
    }

    /**
     * Get all possible legal moves for the piece on {@code startSquare}
     *
     * @param startSquare the selected square in the range 0&ndash;64
     * @return The legal moves from {@code startSquare} in the current position
     */
    public List<Integer> getLegalMoves(int startSquare) {
        return moveGenerator.getLegalMoves(startSquare);
    }

    /**
     * Check whether the game is over and if so, return the reason why.
     *
     * @return {@code null} if the game is not over,
     * otherwise the appropriate enum constant explaining why the game is over.
     */
    public GameOverReason getGameOverReason() {
        return moveGenerator.getGameOverReason();
    }


    public boolean isGameOver() {
        if (moveGenerator.getGameOverReason() == null) {
            return true;
        }
        return false;
    }
    /**
     * Make the specified move, updating the current position.
     *
     * @param startSquare the square in the range 0&ndash;64 containing the piece to move
     * @param endSquare   the target square in the range 0&ndash;64
     * @param promotion   the piece to promote to: one of {@code qrnb}
     * @return true if the move was made successfully, false if a promotion is required
     */
    public boolean makeMove(int startSquare, int endSquare, char promotion) {
        if (!moveGenerator.getLegalMoves(startSquare).contains(endSquare)) {
            throw new IllegalArgumentException("Move " + startSquare + ", " + endSquare + " is illegal");
        }
        return boardData.makeMove(startSquare, endSquare, promotion);
    }

}
