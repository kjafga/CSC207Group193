package entity;

import java.util.List;

import static entity.BoardData.*;

public class Board {

    private final BoardData boardData;
    private final FENGenerator fenGenerator;
    private final MoveGenerator moveGenerator;

    public Board() {
        this.boardData = new BoardData();
        this.fenGenerator = new FENGenerator(boardData);
        this.moveGenerator = new MoveGenerator(boardData);
    }

    public Board(String fen) {
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
     * @param startSquare the selected square in the range 0&ndash;64
     * @return The legal moves from {@code startSquare} in the current position
     */
    public List<Integer> getLegalMoves(int startSquare) {
        return moveGenerator.getLegalMoves(startSquare);
    }

    /**
     * Make the specified move, updating the current position.
     *
     * @param startSquare the selected square in the range 0&ndash;64
     * @param endSquare   the target square in the range 0&ndash;64
     * @param promotion   the piece to promote to: one of {@code QRNB}
     * @return true if the move was made successfully, false if a promotion is required
     */
    public boolean makeMove(int startSquare, int endSquare, char promotion) {
        if (!getLegalMoves(startSquare).contains(endSquare)) {
            throw new IllegalArgumentException("Move " + startSquare + "," + endSquare + " is illegal");
        }

        final int startIndex = startSquare + (startSquare & -8);
        final int endIndex = endSquare + (endSquare & -8);
        final int piece = Math.abs(boardData.pieces[startIndex]);
        final int captured = Math.abs(boardData.pieces[endIndex]);
        boolean clearEnPassant = true;

        if (piece == PAWN && endIndex >> 4 == (boardData.color > 0 ? 7 : 0)) {
            switch (promotion) {
                case 'Q' -> boardData.pieces[endIndex] = (byte) (QUEEN * boardData.color);
                case 'R' -> boardData.pieces[endIndex] = (byte) (ROOK * boardData.color);
                case 'N' -> boardData.pieces[endIndex] = (byte) (KNIGHT * boardData.color);
                case 'B' -> boardData.pieces[endIndex] = (byte) (BISHOP * boardData.color);
                default -> {
                    return false;
                }
            }
        } else {
            boardData.pieces[endIndex] = boardData.pieces[startIndex];
        }
        boardData.pieces[startIndex] = 0;

        if (piece == PAWN || captured != 0) {
            boardData.rule50count = 0;
        } else {
            ++boardData.rule50count;
        }

        if (captured == ROOK) {
            removeCastlingRights(endIndex);
        }

        switch (piece) {
            case PAWN -> {
                if (endIndex == boardData.enPassantIndex) {
                    boardData.pieces[endIndex - (boardData.color << 4)] = 0;
                } else if (Math.abs(endIndex - startIndex) == 32) {
                    if (((endIndex + 1) & 0x88) == 0 && boardData.pieces[endIndex + 1] == PAWN * -boardData.color ||
                            ((endIndex - 1) & 0x88) == 0 && boardData.pieces[endIndex - 1] == PAWN * -boardData.color) {
                        boardData.enPassantIndex = endIndex - (boardData.color << 4);
                        clearEnPassant = false;
                    }
                }
            }
            case KING -> {
                boardData.castlingRights &= (boardData.color > 0 ? 12 : 3);
                if (startIndex == 4 && endIndex == 6) {
                    boardData.pieces[7] = 0;
                    boardData.pieces[5] = (byte) (ROOK * boardData.color);
                } else if (startIndex == 4 && endIndex == 2) {
                    boardData.pieces[0] = 0;
                    boardData.pieces[3] = (byte) (ROOK * boardData.color);
                } else if (startIndex == 116 && endIndex == 118) {
                    boardData.pieces[119] = 0;
                    boardData.pieces[117] = (byte) (ROOK * boardData.color);
                } else if (startIndex == 116 && endIndex == 114) {
                    boardData.pieces[112] = 0;
                    boardData.pieces[115] = (byte) (ROOK * boardData.color);
                }
                boardData.kingIndices[boardData.color >>> 31] = endIndex;
            }
            case ROOK -> removeCastlingRights(startIndex);
        }

        boardData.color = -boardData.color;
        if (boardData.color > 0) {
            ++boardData.moveCount;
        }
        if (clearEnPassant) {
            boardData.enPassantIndex = -1;
        }

        return true;
    }

    private void removeCastlingRights(int rookIndex) {
        switch (rookIndex) {
            case 7 -> boardData.castlingRights &= ~1;
            case 0 -> boardData.castlingRights &= ~2;
            case 119 -> boardData.castlingRights &= ~4;
            case 112 -> boardData.castlingRights &= ~8;
        }
    }

}
