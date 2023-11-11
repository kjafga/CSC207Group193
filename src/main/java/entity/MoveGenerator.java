package entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import static entity.Board.*;
import static java.lang.Integer.signum;

class MoveGenerator {

    private static final int[] ROOK_DIRECTIONS = {16, 1, -16, -1};
    private static final int[] BISHOP_DIRECTIONS = {17, -15, -17, 15};
    private static final int[] KNIGHT_DIRECTIONS = {33, 18, -14, -31, -33, -18, 14, 31};

    private final Board board;
    private final ArrayList<Integer> moves = new ArrayList<>(218);

    MoveGenerator(Board board) {
        this.board = board;
    }

    List<Integer> getLegalMoves(int startSquare) {
        if (startSquare < 0 || startSquare >= 64) {
            throw new IllegalArgumentException("Square number out of bounds: " + startSquare);
        }

        final int startIndex = startSquare + (startSquare & -8);
        if (signum(board.pieces[startIndex]) != board.color) {
            // Square doesn't even have a piece of our color
            return Collections.emptyList();
        }

        moves.clear();
        switch (Math.abs(board.pieces[startIndex])) {
            case KING -> {
                generateStepMoves(startIndex, BISHOP_DIRECTIONS);
                generateStepMoves(startIndex, ROOK_DIRECTIONS);
                addCastlingMoves();
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

        return moves;
    }

    private void addCastlingMoves() {
        if (board.color > 0) {
            if ((board.castlingRights & 1) != 0 &&
                    isLegal(-1, -1) && isLegal(4, 5) &&
                    board.pieces[5] == 0 && board.pieces[6] == 0) {
                moves.add(6);
            }
            if ((board.castlingRights & 2) != 0 &&
                    isLegal(-1, -1) && isLegal(4, 3) &&
                    board.pieces[3] == 0 && board.pieces[2] == 0 && board.pieces[1] == 0) {
                moves.add(2);
            }
        } else {
            if ((board.castlingRights & 4) != 0 &&
                    isLegal(-1, -1) && isLegal(116, 117) &&
                    board.pieces[117] == 0 && board.pieces[118] == 0) {
                moves.add(118);
            }
            if ((board.castlingRights & 8) != 0 &&
                    isLegal(-1, -1) && isLegal(116, 115) &&
                    board.pieces[115] == 0 && board.pieces[114] == 0 && board.pieces[113] == 0) {
                moves.add(114);
            }
        }
    }

    private boolean isLegal(int startIndex, int endIndex) {
        int kingIndex = board.kingIndices[board.color > 0 ? 0 : 1];
        if (kingIndex == startIndex) {
            kingIndex = endIndex;
        }

        int index = kingIndex + (board.color << 4) + 1;
        if ((index & 0x88) == 0 && board.pieces[index] == PAWN * -board.color && index != endIndex &&
                !(endIndex == board.enPassantIndex && index == board.enPassantIndex - (board.color << 4))) {
            return false;
        }

        index = kingIndex + (board.color << 4) - 1;
        if ((index & 0x88) == 0 && board.pieces[index] == PAWN * -board.color && index != endIndex &&
                !(endIndex == board.enPassantIndex && index == board.enPassantIndex - (board.color << 4))) {
            return false;
        }

        if (sliderCheck(startIndex, endIndex, kingIndex, ROOK_DIRECTIONS, ROOK) ||
                sliderCheck(startIndex, endIndex, kingIndex, BISHOP_DIRECTIONS, BISHOP))
            return false;

        for (int dir : KNIGHT_DIRECTIONS) {
            index = kingIndex + dir;
            if ((index & 0x88) == 0 && index != endIndex) {
                return false;
            }
        }

        return true;
    }

    private boolean sliderCheck(int startIndex, int endIndex, int kingIndex, int[] directions, int attacker) {
        for (int dir : directions) {
            int index = kingIndex + dir;
            int piece = board.pieces[index] * board.color;

            if ((index & 0x88) == 0) {
                if ((piece == KING || piece == QUEEN || piece == attacker) && index != endIndex) {
                    return true;
                }
                while (((index += dir) & 0x88) == 0) {
                    piece = board.pieces[index] * board.color;
                    if ((piece == QUEEN || piece == attacker) && index != endIndex) {
                        return true;
                    }
                    if (piece != 0 && piece != startIndex) {
                        break;
                    }
                }
            }
        }
        return false;
    }

    private void generatePawnMoves(int startIndex) {
        final int forwardStep = startIndex + 16 * board.color;

        if ((forwardStep & 0x88) == 0 && board.pieces[forwardStep] == 0) {
            moves.add(forwardStep);

            final int startRank = board.color > 0 ? 1 : 6;
            final int doubleForwardStep = forwardStep + 16 * board.color;
            if (startIndex >> 4 == startRank && board.pieces[doubleForwardStep] == 0) {
                moves.add(doubleForwardStep);
            }
        }

        final int captureRight = forwardStep + 1;
        if (captureRight == board.enPassantIndex ||
                (captureRight & 0x88) == 0 && board.pieces[captureRight] == -board.color) {
            moves.add(captureRight);
        }

        final int captureLeft = forwardStep - 1;
        if (captureLeft == board.enPassantIndex ||
                (captureLeft & 0x88) == 0 && board.pieces[captureLeft] == -board.color) {
            moves.add(captureLeft);
        }
    }

    private void generateStepMoves(int startIndex, int[] directions) {
        for (int off: directions) {
            final int index = startIndex + off;
            if ((index & 0x88) == 0 && signum(board.pieces[index]) != board.color) {
                moves.add(index);
            }
        }
    }

    private void generateSlidingMoves(int startIndex, int[] directions) {
        for (int dir : directions) {
            for (int index = startIndex + dir; (index & 0x88) == 0; index += dir) {
                if (signum(board.pieces[index]) != board.color) {
                    moves.add(index);
                }
                if (board.pieces[index] != 0) {
                    return;
                }
            }
        }
    }

}
