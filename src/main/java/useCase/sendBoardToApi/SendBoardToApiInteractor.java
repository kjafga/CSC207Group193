package useCase.sendBoardToApi;

import entity.Board;

public class SendBoardToApiInteractor implements SendBoardToApiInputBoundry {

    private final SendBoardToApiOutputBoundry sendBoardToApiOutputBoundry;
    private final Board board;
    public SendBoardToApiInteractor(SendBoardToApiOutputBoundry sendBoardToApiOutputBoundry, Board board) {
        this.sendBoardToApiOutputBoundry = sendBoardToApiOutputBoundry;
        this.board = board;
    }

    void GET()
    {
        String Endpoint = "https://stockfish.online/api/stockfish.php";
        String fen = "";
        int depth = 0;
        String mode = "";

    }
}
