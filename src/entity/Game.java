package entity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Game {

    /**
     * @return A list of legal moves from the given square
     * in the current position, in extended algebraic notation.
     */
    @NotNull
    List<String> getLegalMoves(String square);

    /**
     * Make the given move on the board.
     *
     * @param move the move in extended algebraic notation
     * @throws IllegalArgumentException If the move is not legal
     */
    void makeMove(@NotNull String move);

    /**
     * @return The current board position in Forsyth&ndash;Edwards Notation (FEN).
     */
    String toString();

}
