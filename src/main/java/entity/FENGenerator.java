package entity;

class FENGenerator {

    // NOTE: Keep this in sync with Board.java!
    private static final char[] pieceNames = new char[]{'K', 'Q', 'R', 'B', 'N', 'P'};

    private final Board board;
    private final StringBuilder builder;

    FENGenerator(Board board) {
        this.board = board;
        this.builder = new StringBuilder(80);
    }

    @Override
    public String toString() {
        builder.setLength(0);
        putPieceData();
        putActiveColor();
        putCastlingRights();
        putEnPassantSquare();
        putRule50Count();
        putMoveCount();
        return builder.toString();
    }

    private void putMoveCount() {
        builder.append(' ');
        builder.append(board.moveCount);
    }

    private void putRule50Count() {
        builder.append(' ');
        builder.append(board.rule50count);
    }

    private void putEnPassantSquare() {
        builder.append(' ');
        if (board.enPassantSquare == -1) {
            builder.append('-');
        } else {
            builder.appendCodePoint('a' + (board.enPassantSquare & 7));
            builder.appendCodePoint('1' + (board.enPassantSquare >> 3));
        }
    }

    private void putCastlingRights() {
        builder.append(' ');
        if ((board.castlingRights & 15) == 0) {
            builder.append('-');
        } else {
            if ((board.castlingRights & 1) != 0) {
                builder.append('K');
            }
            if ((board.castlingRights & 2) != 0) {
                builder.append('Q');
            }
            if ((board.castlingRights & 4) != 0) {
                builder.append('k');
            }
            if ((board.castlingRights & 8) != 0) {
                builder.append('q');
            }
        }
    }

    private void putActiveColor() {
        builder.append(' ');
        builder.append(board.whiteMove ? 'w' : 'b');
    }

    private void putPieceData() {
        long occupied = board.white | board.black;
        long mask = 1L;
        for (int i = 0; i < 64; ) {

            // Empty squares
            if ((occupied & 1) == 0) {
                final int empty = Long.numberOfTrailingZeros(occupied);
                final int remain = 8 - (i & 7);

                if (empty <= remain) {
                    builder.append(empty);
                    if (empty == remain) {
                        builder.append('/');
                    }
                } else {
                    builder.append(remain);
                    builder.append('/');
                    builder.append("8/8/8/8/8/8/8/", 0, (empty - remain) >> 2);
                    if (((empty - remain) & 7) != 0) {
                        builder.append((empty - remain) & 7);
                    }
                }

                occupied >>= empty;
                mask <<= empty;
                i += empty;
                continue;
            }

            for (int j = 0; j < board.pieces.length; ++j) {
                if ((board.pieces[j] & mask) != 0) {
                    if ((board.white & mask) != 0) {
                        builder.append(pieceNames[j]);
                    } else {
                        builder.append(Character.toLowerCase(pieceNames[j]));
                    }
                }
            }

            mask <<= 1;
            occupied >>= 1;
            ++i;
            if ((i & 7) == 0) {
                builder.append('/');
            }
        }
        builder.setLength(builder.length() - 1);
    }

}
