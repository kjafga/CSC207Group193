package entity;

class Board {

    final long[] byColor = new long[]{
            0xffff000000000000L,
            0x000000000000ffffL
    };
    final long[] byPiece = new long[]{
            0x1000000000000010L,
            0x0800000000000008L,
            0x8100000000000081L,
            0x2400000000000024L,
            0x4200000000000042L,
            0x00ff00000000ff00L
    };
    int color = 0;
    int castlingRights = 15;
    int enPassantSquare = -1;
    int rule50count = 0;
    int moveCount = 1;

    long checkers = 0;
    final long[] blockers = new long[]{0, 0};

}
