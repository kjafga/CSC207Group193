package entity;

class FENGenerator {

    // NOTE: Keep this in sync with Board.java!
    private static final char[] pieceNames = {'K', 'Q', 'R', 'B', 'N', 'P'};

    private final BoardData boardData;
    private final StringBuilder builder;

    FENGenerator(BoardData boardData) {
        this.boardData = boardData;
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
        builder.append(boardData.moveCount);
    }

    private void putRule50Count() {
        builder.append(' ');
        builder.append(boardData.rule50count);
    }

    private void putEnPassantSquare() {
        builder.append(' ');
        if (boardData.enPassantIndex == -1) {
            builder.append('-');
        } else {
            builder.appendCodePoint('a' + (boardData.enPassantIndex & 7));
            builder.appendCodePoint('1' + (boardData.enPassantIndex >> 4));
        }
    }

    private void putCastlingRights() {
        builder.append(' ');
        if ((boardData.castlingRights & 15) == 0) {
            builder.append('-');
        } else {
            if ((boardData.castlingRights & 1) != 0) {
                builder.append('K');
            }
            if ((boardData.castlingRights & 2) != 0) {
                builder.append('Q');
            }
            if ((boardData.castlingRights & 4) != 0) {
                builder.append('k');
            }
            if ((boardData.castlingRights & 8) != 0) {
                builder.append('q');
            }
        }
    }

    private void putActiveColor() {
        builder.append(' ');
        builder.append(boardData.color > 0 ? 'w' : 'b');
    }

    private void putPieceData() {
        for (int rank = 7; rank >= 0; --rank) {
            for (int file = 0; file < 8; ++file) {
                byte piece = boardData.pieces[(rank << 4) + file];
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
