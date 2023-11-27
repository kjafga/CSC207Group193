package useCase.sendBoardToApi;

import entity.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SendBoardToApiInteractor implements SendBoardToApiInputBoundary {

    private static final String STOCKFISH_API_URL = "https://stockfish.online/api/stockfish.php?fen=%s&depth=%d&mode=bestmove";
    private static final Pattern BESTMOVE_PATTERN = Pattern.compile("bestmove\\s++([a-h][1-8][a-h][1-8][qrnb]?+)");

    private final SendBoardToApiOutputBoundary sendBoardToApiOutputBoundary;
    private final Board board;

    public SendBoardToApiInteractor(SendBoardToApiOutputBoundary sendBoardToApiOutputBoundary, Board board) {
        this.sendBoardToApiOutputBoundary = sendBoardToApiOutputBoundary;
        this.board = board;
    }

    @Override
    public void execute() throws IOException {
        String move = getBestMove(board);

        int startSquare = move.charAt(0) - 'a' + ((move.charAt(1) - '1') << 3);
        int endSquare = move.charAt(2) - 'a' + ((move.charAt(3) - '1') << 3);
        char promotion = move.length() >= 5 ? move.charAt(4) : '?';
        board.makeMove(startSquare, endSquare, promotion);

        SendBoardToApiOutputData outputData = new SendBoardToApiOutputData(board.toString().split(" ")[0]);
        sendBoardToApiOutputBoundary.prepareSuccessView(outputData);
    }

    private static String getBestMove(Board board) throws IOException {
        // noinspection deprecation: We want to use URL so that we don't have to manually escape the FEN
        URL url = new URL(String.format(STOCKFISH_API_URL, board, 1));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line = reader.readLine();
            Matcher m = BESTMOVE_PATTERN.matcher(line);
            if (!m.find()) {
                throw new IllegalStateException("Stockfish API sent line: " + line);
            }
            return m.group(1);
        }
    }

}
