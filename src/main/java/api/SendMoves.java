package api;

import useCase.movePiece.MovePieceInputBoundry;
import view.View;

public class SendMoves extends View implements MovePieceInputBoundry {
    void GET()
    {
        String Endpoint = "https://stockfish.online/api/stockfish.php";
        String fen = "";
        int depth = 0;
        String mode = "";

    }
}
