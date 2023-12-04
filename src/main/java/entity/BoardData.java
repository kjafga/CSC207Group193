package entity;

final class BoardData {

    static final byte KING = 1;
    static final byte QUEEN = 2;
    static final byte ROOK = 3;
    static final byte BISHOP = 4;
    static final byte KNIGHT = 5;
    static final byte PAWN = 6;

    final byte[] pieces = {
            ROOK,  KNIGHT,  BISHOP,  QUEEN,  KING,  BISHOP,  KNIGHT,  ROOK,  99, 99, 99, 99, 99, 99, 99, 99,
            PAWN,  PAWN,    PAWN,    PAWN,   PAWN,  PAWN,    PAWN,    PAWN,  99, 99, 99, 99, 99, 99, 99, 99,
            0,     0,       0,       0,      0,     0,       0,       0,     99, 99, 99, 99, 99, 99, 99, 99,
            0,     0,       0,       0,      0,     0,       0,       0,     99, 99, 99, 99, 99, 99, 99, 99,
            0,     0,       0,       0,      0,     0,       0,       0,     99, 99, 99, 99, 99, 99, 99, 99,
            0,     0,       0,       0,      0,     0,       0,       0,     99, 99, 99, 99, 99, 99, 99, 99,
            -PAWN, -PAWN,   -PAWN,   -PAWN,  -PAWN, -PAWN,   -PAWN,   -PAWN, 99, 99, 99, 99, 99, 99, 99, 99,
            -ROOK, -KNIGHT, -BISHOP, -QUEEN, -KING, -BISHOP, -KNIGHT, -ROOK, 99, 99, 99, 99, 99, 99, 99, 99
    };

    int color = 1;
    int castlingRights = 15;
    int enPassantIndex = -1;
    int rule50count = 0;
    int moveCount = 1;

    final int[] kingIndices = {4, 116};

    boolean makeMove(int startSquare, int endSquare, char promotion) {
        final int startIndex = startSquare + (startSquare & -8);
        final int endIndex = endSquare + (endSquare & -8);
        final int piece = Math.abs(pieces[startIndex]);
        final int captured = Math.abs(pieces[endIndex]);
        boolean clearEnPassant = true;

        if (piece == PAWN && endIndex >> 4 == (color > 0 ? 7 : 0)) {
            switch (promotion) {
                case 'q' -> pieces[endIndex] = (byte) (QUEEN * color);
                case 'r' -> pieces[endIndex] = (byte) (ROOK * color);
                case 'n' -> pieces[endIndex] = (byte) (KNIGHT * color);
                case 'b' -> pieces[endIndex] = (byte) (BISHOP * color);
                default -> {
                    return false;
                }
            }
        } else {
            pieces[endIndex] = pieces[startIndex];
        }
        pieces[startIndex] = 0;

        if (piece == PAWN || captured != 0) {
            rule50count = 0;
        } else {
            ++rule50count;
        }

        if (captured == ROOK) {
            removeCastlingRights(endIndex);
        }

        switch (piece) {
            case PAWN -> {
                if (endIndex == enPassantIndex) {
                    pieces[endIndex - (color << 4)] = 0;
                } else if (Math.abs(endIndex - startIndex) == 32) {
                    if (((endIndex + 1) & 0x88) == 0 && pieces[endIndex + 1] == PAWN * -color ||
                            ((endIndex - 1) & 0x88) == 0 && pieces[endIndex - 1] == PAWN * -color) {
                        enPassantIndex = endIndex - (color << 4);
                        clearEnPassant = false;
                    }
                }
            }
            case KING -> {
                castlingRights &= (color > 0 ? 12 : 3);
                if (startIndex == 4 && endIndex == 6) {
                    pieces[7] = 0;
                    pieces[5] = (byte) (ROOK * color);
                } else if (startIndex == 4 && endIndex == 2) {
                    pieces[0] = 0;
                    pieces[3] = (byte) (ROOK * color);
                } else if (startIndex == 116 && endIndex == 118) {
                    pieces[119] = 0;
                    pieces[117] = (byte) (ROOK * color);
                } else if (startIndex == 116 && endIndex == 114) {
                    pieces[112] = 0;
                    pieces[115] = (byte) (ROOK * color);
                }
                kingIndices[color >>> 31] = endIndex;
            }
            case ROOK -> removeCastlingRights(startIndex);
        }

        color = -color;
        if (color > 0) {
            ++moveCount;
        }
        if (clearEnPassant) {
            enPassantIndex = -1;
        }

        return true;
    }

    private void removeCastlingRights(int rookIndex) {
        switch (rookIndex) {
            case 7 -> castlingRights &= ~1;
            case 0 -> castlingRights &= ~2;
            case 119 -> castlingRights &= ~4;
            case 112 -> castlingRights &= ~8;
        }
    }

}
