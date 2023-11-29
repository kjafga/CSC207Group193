package interfaceAdapters.newGame;

import useCase.newGame.NewGameInputBoundary;
public class NewGameController {
//    private final MovePieceInputBoundary movePieceInputInteractor;
//
//    public MovePieceController(MovePieceInputBoundary movePieceInputInteractor) {
//        this.movePieceInputInteractor = movePieceInputInteractor;
//    }
//
//    public void execute(int startSquare, int endSquare, char promotion){
//        MovePieceInputData inputData = new MovePieceInputData(startSquare, endSquare, promotion);
//        movePieceInputInteractor.movePiece(inputData);
//    }
    private final NewGameInputBoundary newGameInputInteractor;

    public NewGameController(NewGameInputBoundary newGameInputInteractor) {
        this.newGameInputInteractor = newGameInputInteractor;
    }
    public void execute(){
        newGameInputInteractor.execute();
    }
}
