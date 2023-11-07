package entity;

class Board {

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
    int enPassantSquare = -1;
    int rule50count = 0;
    int moveCount = 1;

}
