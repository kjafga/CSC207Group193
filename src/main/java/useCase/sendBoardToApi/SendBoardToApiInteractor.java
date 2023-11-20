package useCase.sendBoardToApi;

import entity.Board;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SendBoardToApiInteractor implements SendBoardToApiInputBoundary {

    private final SendBoardToApiOutputBoundary sendBoardToApiOutputBoundary;
    private final Board board;

    public SendBoardToApiInteractor(SendBoardToApiOutputBoundary sendBoardToApiOutputBoundary, Board board) {
        this.sendBoardToApiOutputBoundary = sendBoardToApiOutputBoundary;
        this.board = board;
    }

    @Override
    public void execute() throws IOException {
        URL url = new URL("https://stockfish.online/api/stockfish.php");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("fen", board.toString());
        connection.setRequestProperty("depth", "13");
        connection.setRequestProperty("mode", "bestmove");

        Scanner scanner = new Scanner(connection.getInputStream());
        scanner.next("bestmove");

        String move = scanner.next("[a-h][1-8][a-h][1-8][qrnb]?");

        int startSquare = move.charAt(0) - 'a' + ((move.charAt(1) - '1') << 3);
        int endSquare = move.charAt(1) - 'a' + ((move.charAt(2) - '1') << 3);
        char promotion = move.length() >= 5 ? move.charAt(4) : '?';
        board.makeMove(startSquare, endSquare, promotion);

        SendBoardToApiOutputData outputData = new SendBoardToApiOutputData(board.toString().split(" ")[0]);
        sendBoardToApiOutputBoundary.prepareSuccessView(outputData);
    }
}
