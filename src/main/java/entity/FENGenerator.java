package entity;

import java.util.Arrays;

class FENGenerator {

    // NOTE: Keep this in sync with Board.java!
    private static final char[] pieceNames = {'K', 'Q', 'R', 'B', 'N', 'P'};

    private final BoardData boardData;
    private final StringBuilder builder;

    FENGenerator(BoardData boardData) {
        this.boardData = boardData;
        this.builder = new StringBuilder(80);
    }

    static BoardData fromFen(String fen) {
        BoardData data = new BoardData();
        String[] fields = fen.split(" ");
        int i = 112;

        Arrays.fill(data.pieces, (byte) 99);
        for (char c : fields[0].toCharArray()) {
            if (c == '/') {
                if ((i & 15) != 8) {
                    throw invalidFEN(fen);
                }
                i -= 24;
            } else if (c >= '1' && c <= '8') {
                int to = i + c - '0';
                Arrays.fill(data.pieces, i, to, (byte) 0);
                i = to;
            } else {
                data.pieces[i] = 1;
                while (pieceNames[data.pieces[i] - 1] != Character.toUpperCase(c)) {
                    ++data.pieces[i];
                }
                if (Character.isLowerCase(c)) {
                    data.pieces[i] = (byte) (-data.pieces[i]);
                }
                if (c == 'K') {
                    data.kingIndices[0] = i;
                } else if (c == 'k') {
                    data.kingIndices[1] = i;
                }
                ++i;
            }
        }

        if (fields[1].equals("w")) {
            data.color = 1;
        } else if (fields[1].equals("b")) {
            data.color = -1;
        } else {
            throw invalidFEN(fen);
        }

        data.castlingRights = 0;
        if (!fields[2].equals("-")) {
            for (i = 0; i < fields[2].length(); ++i) {
                data.castlingRights |= switch (fields[2].charAt(i)) {
                    case 'K' -> 1;
                    case 'Q' -> 2;
                    case 'k' -> 4;
                    case 'q' -> 8;
                    default -> throw invalidFEN(fen);
                };
                if (i > 0 && fields[2].charAt(i - 1) >= fields[2].charAt(i)) {
                    throw invalidFEN(fen);
                }
            }
        }

        if (fields[3].equals("-")) {
            data.enPassantIndex = -1;
        } else if (fields[3].length() != 2) {
            throw invalidFEN(fen);
        } else {
            int file = fields[3].charAt(0) - 'a';
            int rank = fields[3].charAt(1) - '1';
            if (rank < 0 || rank >= 8 || file < 0 || file >= 8) {
                throw invalidFEN(fen);
            }
            data.enPassantIndex = (rank << 4) + file;
        }

        try {
            data.rule50count = Integer.parseInt(fields[4]);
            data.moveCount = Integer.parseInt(fields[5]);
        } catch (NumberFormatException ignored) {
            throw invalidFEN(fen);
        }
        if (data.rule50count < 0 || data.rule50count > 100 || data.moveCount < 1) {
            throw invalidFEN(fen);
        }

        return data;
    }

    private static IllegalArgumentException invalidFEN(String fen) {
        return new IllegalArgumentException("Malformed FEN: " + fen);
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
