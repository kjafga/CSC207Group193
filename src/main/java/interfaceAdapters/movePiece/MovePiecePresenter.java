package interfaceAdapters.movePiece;

import interfaceAdapters.ViewManagerModel;
import useCase.movePiece.MovePieceOutputBoundry;

public class MovePiecePresenter implements MovePieceOutputBoundry {

    private final MovePieceViewModel movePieceViewModel;
    private ViewManagerModel viewManagerModel;

    public MovePiecePresenter(MovePieceViewModel movePieceViewModel) {
        this.movePieceViewModel = movePieceViewModel;
    }
}
