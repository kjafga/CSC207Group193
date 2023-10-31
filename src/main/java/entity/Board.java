package entity;

class Board {

    private static final int KING = 0;
    private static final int QUEEN = 1;
    private static final int ROOK = 2;
    private static final int BISHOP = 3;
    private static final int KNIGHT = 4;
    private static final int PAWN = 5;

    long white, black;
    final long[] pieces = new long[6];
    boolean whiteMove;
    int castlingRights;
    int enPassantSquare;
    int rule50count, moveCount;

    private long checkers, blockers;

    public Board() {
        white = 0xffff000000000000L;
        black = 0x000000000000ffffL;

        pieces[KING] = (1L << 4) | (1L << 60);
        pieces[QUEEN] = (1L << 3) | (1L << 59);
        pieces[ROOK] = 1L | (1L << 7) | (1L << 56) | (1L << 63);
        pieces[BISHOP] = (1L << 2) | (1L << 5) | (1L << 58) | (1L << 61);
        pieces[KNIGHT] = (1L << 1) | (1L << 6) | (1L << 57) | (1L << 62);
        pieces[PAWN] = 0x00ff00000000ff00L;

        whiteMove = true;

        castlingRights = 15;

        enPassantSquare = -1;

        rule50count = 0;
        moveCount = 1;
    }

}
