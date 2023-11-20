package useCase.sendBoardToApi;

import entity.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SendBoardToApiInteractor implements SendBoardToApiInputBoundary {

    private final SendBoardToApiOutputBoundary sendBoardToApiOutputBoundary;
    private final Board board;

    public SendBoardToApiInteractor(SendBoardToApiOutputBoundary sendBoardToApiOutputBoundary, Board board) {
        this.sendBoardToApiOutputBoundary = sendBoardToApiOutputBoundary;
        this.board = board;
    }

    @Override
    public void execute() throws IOException {
        URL url = new URL(String.format(
                "https://stockfish.online/api/stockfish.php?fen=%s&depth=%d&mode=bestmove",
                board.toString(),
                13
        ));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        String move;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line = reader.readLine();
            // TODO XXX HACK HACK HACK
            System.err.println(line);
            move = line.split(" ")[2];
            System.err.println(move);
        }

        int startSquare = move.charAt(0) - 'a' + ((move.charAt(1) - '1') << 3);
        int endSquare = move.charAt(2) - 'a' + ((move.charAt(3) - '1') << 3);
        char promotion = move.length() >= 5 ? move.charAt(4) : '?';
        board.makeMove(startSquare, endSquare, promotion);

        SendBoardToApiOutputData outputData = new SendBoardToApiOutputData(board.toString().split(" ")[0]);
        sendBoardToApiOutputBoundary.prepareSuccessView(outputData);
    }
}
