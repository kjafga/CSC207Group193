package useCase.sendBoardToApi;

public class SendBoardToApiInteractor implements SendBoardToApiInputBoundry {

    private final SendBoardToApiOutputBoundry sendBoardToApiOutputBoundry;

    public SendBoardToApiInteractor(SendBoardToApiOutputBoundry sendBoardToApiOutputBoundry) {
        this.sendBoardToApiOutputBoundry = sendBoardToApiOutputBoundry;
    }

    void GET()
    {
        String Endpoint = "https://stockfish.online/api/stockfish.php";
        String fen = "";
        int depth = 0;
        String mode = "";

    }
}
