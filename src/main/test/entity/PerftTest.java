package entity;

import org.junit.After;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Formatter;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

// https://www.chessprogramming.org/Perft_Results
public class PerftTest {

    private static final Pattern PERFT_PAT = Pattern.compile("([a-h][1-8][a-h][1-8][qrnb]?):\\s+(\\d+)");

    private final Formatter in;
    private final BufferedReader out;

    public PerftTest() throws IOException {
        Process stockfish = new ProcessBuilder()
                .command(System.getenv("STOCKFISH_PATH"))
                .start();
        this.in = new Formatter(stockfish.outputWriter());
        this.out = stockfish.inputReader();
    }

    @After
    public void cleanup() {
        in.close();
        if (in.ioException() != null) {
            in.ioException().printStackTrace();
        }
    }

    private Map<String, Long> getExpectedPerft(String fen, int depth) throws IOException {
        Map<String, Long> result = new TreeMap<>();
        String line;

        in.format("position fen %s\n", fen);
        in.format("go perft %d\n", depth);
        in.flush();

        while ((line = out.readLine()) != null && !line.startsWith("Nodes searched:")) {
            Matcher m = PERFT_PAT.matcher(line);
            if (m.matches()) {
                result.put(m.group(1), Long.parseLong(m.group(2)));
            }
        }

        return result;
    }

    private Map<String, Long> getActualPerft(String fen, int depth) {
        if (depth <= 0) {
            return Map.of("", 1L);
        }

        Map<String, Long> result = new TreeMap<>();
        Board b = new Board(fen);

        for (int i = 0; i < 64; ++i) {
            Integer[] legalMoves = b.getLegalMoves(i).toArray(new Integer[0]);
            for (int j : legalMoves) {
                StringBuilder builder = new StringBuilder(5);
                builder.appendCodePoint('a' + (i & 7));
                builder.appendCodePoint('1' + (i >> 3));
                builder.appendCodePoint('a' + (j & 7));
                builder.appendCodePoint('1' + (j >> 3));

                if (b.makeMove(i, j, '?')) {
                    result.put(builder.toString(), getActualPerft(b.toString(), depth - 1).values().stream()
                            .mapToLong(Long::longValue).sum());
                    b = new Board(fen);
                } else {
                    builder.append('?');
                    for (char promotion : "qrnb".toCharArray()) {
                        b.makeMove(i, j, promotion);
                        builder.setCharAt(4, promotion);
                        result.put(builder.toString(), getActualPerft(b.toString(), depth - 1).values().stream()
                                .mapToLong(Long::longValue).sum());
                        b = new Board(fen);
                    }
                }
            }
        }
        return result;
    }

    private void comparePerft(String fen, int depth) throws IOException {
        Map<String, Long> expected;
        Map<String, Long> actual;

        Set<String> expectedKeys;
        Set<String> actualKeys;

        outer:
        for (;;) {
            expected = getExpectedPerft(fen, depth);
            actual = getActualPerft(fen, depth);

            expectedKeys = expected.keySet();
            actualKeys = actual.keySet();

            if (!expectedKeys.containsAll(actualKeys)) {
                actualKeys.removeAll(expectedKeys);
                fail(String.format("FEN %s depth %d: extra moves %s\n", fen, depth, actualKeys));
            }
            if (!actualKeys.containsAll(expectedKeys)) {
                expectedKeys.removeAll(actualKeys);
                fail(String.format("FEN %s depth %d: missing moves %s\n", fen, depth, expectedKeys));
            }
            for (String key : actualKeys) {
                long expectedCount = expected.get(key);
                long actualCount = actual.get(key);
                if (expectedCount != actualCount) {
                    System.err.printf("FEN %s depth %d move %s: expected %d got %d\n",
                            fen, depth, key,
                            expectedCount, actualCount);
                    fen = getNewFEN(fen, key);
                    --depth;
                    continue outer;
                }
            }
            return;
        }
    }

    private String getNewFEN(String fen, String move) throws IOException {
        String newFEN = null;
        Board newBoard;

        in.format("position fen %s moves %s\n", fen, move);
        in.format("d\n");
        in.flush();

        String line;
        while ((line = out.readLine()) != null) {
            if (line.startsWith("Fen:")) {
                newFEN = line.replaceFirst("Fen:\\s+", "");
                break;
            }
        }
        out.readLine();
        out.readLine();

        newBoard = new Board(fen);
        newBoard.makeMove(
                (move.charAt(0) - 'a') + ((move.charAt(1) - '1') << 3),
                (move.charAt(2) - 'a') + ((move.charAt(3) - '1') << 3),
                move.length() >= 5 ? move.charAt(4) : '?'
        );
        assertEquals(String.format("Disagreement on %s + %s:", fen, move),
                newFEN, newBoard.toString());
        return newFEN;
    }

    @Test
    public void root() throws IOException {
        comparePerft("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", 6);
    }

    @Test
    public void kiwipete() throws IOException {
        comparePerft("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1", 6);
    }

    @Test
    public void pos3() throws IOException {
        comparePerft("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - - 0 1", 6);
    }

    @Test
    public void pos4() throws IOException {
        comparePerft("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1", 6);
    }

    @Test
    public void pos4reversed() throws IOException {
        comparePerft("r2q1rk1/pP1p2pp/Q4n2/bbp1p3/Np6/1B3NBn/pPPP1PPP/R3K2R b KQ - 0 1", 6);
    }

    @Test
    public void pos5() throws IOException {
        comparePerft("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8", 5);
    }

    @Test
    public void pos6() throws IOException {
        comparePerft("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10", 6);
    }

}
