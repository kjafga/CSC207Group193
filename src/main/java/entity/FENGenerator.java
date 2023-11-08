package entity;

class FENGenerator {

    // NOTE: Keep this in sync with Board.java!
    private static final char[] pieceNames = {'K', 'Q', 'R', 'B', 'N', 'P'};

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
        if (board.enPassantIndex == -1) {
            builder.append('-');
        } else {
            builder.appendCodePoint('a' + (board.enPassantIndex & 7));
            builder.appendCodePoint('1' + (board.enPassantIndex >> 4));
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
        builder.append(board.color > 0 ? 'w' : 'b');
    }

    private void putPieceData() {
        for (int rank = 7; rank >= 0; --rank) {
            for (int file = 0; file < 8; ++file) {
                byte piece = board.pieces[(rank << 4) + file];
                if (piece == 0) {
                    int lastIndex = builder.length() - 1;
                    char c = lastIndex >= 0 ? builder.charAt(lastIndex) : 0;
                    if (c >= '1' && c < '8') {
                        builder.setCharAt(lastIndex, (char) (c + 1));
                    } else {
                        builder.append('1');
                    }
                } else {
                    char pieceName = pieceNames[Math.abs(piece) - 1];
                    builder.append(piece > 0 ? pieceName : Character.toLowerCase(pieceName));
                }
            }
            if (rank > 0) {
                builder.append('/');
            }
        }
    }

}
