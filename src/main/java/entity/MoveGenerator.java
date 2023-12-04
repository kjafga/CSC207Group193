package entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import static entity.BoardData.*;
import static java.lang.Integer.signum;
import static java.util.Collections.disjoint;
import static java.util.Collections.emptyList;

final class MoveGenerator {

    private static final int[] ROOK_DIRECTIONS = {16, 1, -16, -1};
    private static final int[] BISHOP_DIRECTIONS = {17, -15, -17, 15};
    private static final int[] KNIGHT_DIRECTIONS = {33, 18, -14, -31, -33, -18, 14, 31};

    private static final Set<Byte> MATING_MATERIAL = Set.of(PAWN, ROOK, QUEEN);

    private final BoardData boardData;
    private final ArrayList<Integer> moves = new ArrayList<>(218);

    MoveGenerator(BoardData boardData) {
        this.boardData = boardData;
    }

    List<Integer> getLegalMoves(int startSquare) {
        if (startSquare < 0 || startSquare >= 64) {
            throw new IllegalArgumentException("Square number out of bounds: " + startSquare);
        }

        final int startIndex = startSquare + (startSquare & -8);
        if (signum(boardData.pieces[startIndex]) != boardData.color) {
            // Square doesn't even have a piece of our color
            return emptyList();
        }

        moves.clear();
        switch (Math.abs(boardData.pieces[startIndex])) {
            case KING -> {
                generateStepMoves(startIndex, BISHOP_DIRECTIONS);
                generateStepMoves(startIndex, ROOK_DIRECTIONS);
                if (!inCheck()) {
                    addCastlingMoves();
                }
            }
            case QUEEN -> {
                generateSlidingMoves(startIndex, ROOK_DIRECTIONS);
                generateSlidingMoves(startIndex, BISHOP_DIRECTIONS);
            }
            case ROOK -> generateSlidingMoves(startIndex, ROOK_DIRECTIONS);
            case BISHOP -> generateSlidingMoves(startIndex, BISHOP_DIRECTIONS);
            case KNIGHT -> generateStepMoves(startIndex, KNIGHT_DIRECTIONS);
            case PAWN -> generatePawnMoves(startIndex);
            default -> throw new AssertionError("This can't happen.  Call Daniel!");
        }

        final ListIterator<Integer> it = moves.listIterator();
        while (it.hasNext()) {
            final int endIndex = it.next();
            if (isLegal(startIndex, endIndex)) {
                it.set(endIndex + (endIndex & 7) >> 1);
            } else {
                it.remove();
            }
        }

        return Collections.unmodifiableList(moves);
    }

    GameOverReason getGameOverReason() {
        if (boardData.rule50count >= 100) {
            return GameOverReason.FIFTY_MOVE;
        }

        final Set<Byte> material = new HashSet<>();
        int whiteCount = 0;
        int blackCount = 0;
        int bishopFlags = 0;
        boolean hasMoves = false;

        for (int i = 0; i < 64; ++i) {
            if (!getLegalMoves(i).isEmpty()) {
                hasMoves = true;
            }
            final byte piece = boardData.pieces[i + (i & -8)];
            if (piece == 0 || piece == KING || piece == -KING) {
                continue;
            }
            if (piece > 0) {
                material.add(piece);
                ++whiteCount;
            } else {
                material.add((byte) -piece);
                ++blackCount;
            }
            if (piece == BISHOP) {
                bishopFlags |= ((i ^ i >> 3) & 1) + 1;
            } else if (piece == -BISHOP) {
                bishopFlags |= ((i ^ i >> 3) & 1) + 1 << 2;
            }
        }
        if (!hasMoves) {
            return inCheck() ? GameOverReason.CHECKMATE : GameOverReason.STALEMATE;
        }
        if (!disjoint(material, MATING_MATERIAL)) {
            return null;
        }
        if (material.equals(Set.of(BISHOP))) {
            // If the only pieces remaining are bishops,
            // they must be bishops of opposite colors,
            // otherwise nobody can mate.
            if (bishopFlags == 0b1001 || bishopFlags == 0b0110) {
                return null;
            }
            return GameOverReason.INSUFFICIENT_MATERIAL;
        }
        if (blackCount >= 2 || whiteCount >= 2) {
            // If a side has at least two pieces, then they have mating material
            // (except in the same-color bishop case, which was handled earlier)
            return null;
        }
        // If the only piece on the board is a knight then nobody can mate
        if ((blackCount + whiteCount) == 1 && material.equals(Set.of(KNIGHT))) {
            return GameOverReason.INSUFFICIENT_MATERIAL;
        }
        return null;
    }

    private boolean inCheck() {
        return !isLegal(-1, -1); // Stupid way of testing if we are in check
    }

    private void addCastlingMoves() {
        if (boardData.color > 0) {
            if ((boardData.castlingRights & 1) != 0 && isLegal(4, 5) &&
                    boardData.pieces[5] == 0 && boardData.pieces[6] == 0) {
                moves.add(6);
            }
            if ((boardData.castlingRights & 2) != 0 && isLegal(4, 3) &&
                    boardData.pieces[3] == 0 && boardData.pieces[2] == 0 && boardData.pieces[1] == 0) {
                moves.add(2);
            }
        } else {
            if ((boardData.castlingRights & 4) != 0 && isLegal(116, 117) &&
                    boardData.pieces[117] == 0 && boardData.pieces[118] == 0) {
                moves.add(118);
            }
            if ((boardData.castlingRights & 8) != 0 && isLegal(116, 115) &&
                    boardData.pieces[115] == 0 && boardData.pieces[114] == 0 && boardData.pieces[113] == 0) {
                moves.add(114);
            }
        }
    }

    private boolean isLegal(int startIndex, int endIndex) {
        int kingIndex = boardData.kingIndices[boardData.color >>> 31];
        if (kingIndex == startIndex) {
            kingIndex = endIndex;
        }

        int index = kingIndex + (boardData.color << 4) + 1;
        if ((index & 0x88) == 0 && boardData.pieces[index] == PAWN * -boardData.color && index != endIndex &&
                !(endIndex == boardData.enPassantIndex && index == boardData.enPassantIndex - (boardData.color << 4))) {
            return false;
        }

        index = kingIndex + (boardData.color << 4) - 1;
        if ((index & 0x88) == 0 && boardData.pieces[index] == PAWN * -boardData.color && index != endIndex &&
                !(endIndex == boardData.enPassantIndex && index == boardData.enPassantIndex - (boardData.color << 4))) {
            return false;
        }

        if (sliderCheck(startIndex, endIndex, kingIndex, ROOK_DIRECTIONS, ROOK) ||
                sliderCheck(startIndex, endIndex, kingIndex, BISHOP_DIRECTIONS, BISHOP))
            return false;

        for (int dir : KNIGHT_DIRECTIONS) {
            index = kingIndex + dir;
            if ((index & 0x88) == 0 && boardData.pieces[index] == KNIGHT * -boardData.color && index != endIndex) {
                return false;
            }
        }

        return true;
    }

    private boolean sliderCheck(int startIndex, int endIndex, int kingIndex, int[] directions, int attacker) {
        for (int dir : directions) {
            for (int index = kingIndex + dir; (index & 0x88) == 0; index += dir) {
                int piece = boardData.pieces[index] * -boardData.color;
                if (index == kingIndex + dir && piece == KING && index != endIndex ||
                        (piece == QUEEN || piece == attacker) && index != endIndex) {
                    return true;
                }
                if (index == endIndex) {
                    // The newly placed piece blocks this ray
                    break;
                }
                if (endIndex == boardData.enPassantIndex &&
                        startIndex >= 0 && boardData.pieces[startIndex] == PAWN * boardData.color &&
                        index == boardData.enPassantIndex - (boardData.color << 4)) {
                    // The pawn that used to block this ray was en passanted away
                    continue;
                }
                if (index == startIndex) {
                    // The piece that used to block this ray was moved
                    continue;
                }
                if (piece != 0) {
                    // A piece that didn't move blocks this ray
                    break;
                }
            }
        }
        return false;
    }

    private void generatePawnMoves(int startIndex) {
        final int forwardStep = startIndex + (boardData.color << 4);

        if ((forwardStep & 0x88) == 0 && boardData.pieces[forwardStep] == 0) {
            moves.add(forwardStep);

            final int startRank = boardData.color > 0 ? 1 : 6;
            final int doubleForwardStep = forwardStep + (boardData.color << 4);
            if (startIndex >> 4 == startRank && boardData.pieces[doubleForwardStep] == 0) {
                moves.add(doubleForwardStep);
            }
        }

        final int captureRight = forwardStep + 1;
        if (captureRight == boardData.enPassantIndex ||
                (captureRight & 0x88) == 0 && signum(boardData.pieces[captureRight]) == -boardData.color) {
            moves.add(captureRight);
        }

        final int captureLeft = forwardStep - 1;
        if (captureLeft != -1 && captureLeft == boardData.enPassantIndex ||
                (captureLeft & 0x88) == 0 && signum(boardData.pieces[captureLeft]) == -boardData.color) {
            moves.add(captureLeft);
        }
    }

    private void generateStepMoves(int startIndex, int[] directions) {
        for (int off: directions) {
            final int index = startIndex + off;
            if ((index & 0x88) == 0 && signum(boardData.pieces[index]) != boardData.color) {
                moves.add(index);
            }
        }
    }

    private void generateSlidingMoves(int startIndex, int[] directions) {
        for (int dir : directions) {
            for (int index = startIndex + dir; (index & 0x88) == 0; index += dir) {
                if (signum(boardData.pieces[index]) != boardData.color) {
                    moves.add(index);
                }
                if (boardData.pieces[index] != 0) {
                    break;
                }
            }
        }
    }

}
