package useCase.book;

import entity.Board;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.awt.print.Book;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BookInteractor implements BookInputBoundary {

    public static final String OPENING_BOOK_URL = "https://explorer.lichess.ovh/masters?play=%s";

    private final BookOutputBoundary outputBoundary;
    private final Board board;

    public BookInteractor(BookOutputBoundary outputBoundary, Board board) {
        this.outputBoundary = outputBoundary;
        this.board = board;
    }

    @Override
    public void getBookMoves() throws IOException {
        //noinspection deprecation
        URL url = new URL(String.format(OPENING_BOOK_URL, String.join(",", board.getPlayedMoves())));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        JSONObject json = new JSONObject(new JSONTokener(connection.getInputStream()));
        System.err.println(url);
        System.err.println(json);

        JSONObject opening = json.optJSONObject("opening");
        if (opening == null) {
            outputBoundary.prepareSuccessView(new BookOutputData("", Collections.emptyMap()));
            return;
        }

        final String openingName = opening.getString("eco") + " " + opening.getString("name");
        final Map<String, int[]> bookMoves = new HashMap<>();

        for (Object o : json.getJSONArray("moves")) {
            final JSONObject moveData = (JSONObject) o;
            final int[] wdb = new int[3];

            wdb[0] = moveData.getInt("white");
            wdb[1] = moveData.getInt("draws");
            wdb[2] = moveData.getInt("black");

            bookMoves.put(moveData.getString("uci"), wdb);
        }

        outputBoundary.prepareSuccessView(new BookOutputData(openingName, bookMoves));
    }

}
