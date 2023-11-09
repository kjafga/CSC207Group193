package entity;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

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
        final int piece = board.pieces[startIndex];
        if (signum(piece) != board.color) {
            // Square doesn't even have a piece of our color
            return Collections.emptyList();
        }

        moves.clear();
        switch (Math.abs(piece)) {
            case KING -> {
                generateStepMoves(startIndex, BISHOP_DIRECTIONS);
                generateStepMoves(startIndex, ROOK_DIRECTIONS);
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

        return moves;
    }

    private void generatePawnMoves(int startIndex) {
        final int forwardStep = startIndex + 16 * board.color;

        if ((forwardStep & 0x88) == 0 && board.pieces[forwardStep] == 0) {
            moves.add(forwardStep + (forwardStep & 7) >> 1);

            final int startRank = board.color > 0 ? 1 : 6;
            final int doubleForwardStep = forwardStep + 16 * board.color;
            if (startIndex >> 4 == startRank && board.pieces[doubleForwardStep] == 0) {
                moves.add(doubleForwardStep + (doubleForwardStep & 7) >> 1);
            }
        }

        final int captureRight = forwardStep + 1;
        if (captureRight == board.enPassantIndex ||
                (captureRight & 0x88) == 0 && board.pieces[captureRight] == -board.color) {
            moves.add(forwardStep + 1 + (captureRight & 7) >> 1);
        }

        final int captureLeft = forwardStep - 1;
        if (captureLeft == board.enPassantIndex ||
                (captureLeft & 0x88) == 0 && board.pieces[captureLeft] == -board.color) {
            moves.add(captureLeft + (captureLeft & 7) >> 1);
        }
    }

    private void generateStepMoves(int startIndex, int[] directions) {
        for (int off: directions) {
            final int endIndex = startIndex + off;
            if ((endIndex & 0x88) == 0 && signum(board.pieces[endIndex]) != board.color) {
                moves.add(endIndex + (endIndex & 7) >> 1);
            }
        }
    }

    private void generateSlidingMoves(int startIndex, int[] directions) {
        for (int dir : directions) {
            for (int endIndex = startIndex + dir; (endIndex & 0x88) == 0; endIndex += dir) {
                if (signum(board.pieces[endIndex]) != board.color) {
                    moves.add(endIndex + (endIndex & 7) >> 1);
                }
                if (board.pieces[endIndex] != 0) {
                    return;
                }
            }
        }
    }

}
