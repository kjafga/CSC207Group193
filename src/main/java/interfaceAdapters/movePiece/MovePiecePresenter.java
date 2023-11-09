package interfaceAdapters.movePiece;

import interfaceAdapters.ViewManagerModel;
import useCase.movePiece.MovePieceOutputBoundary;

public class MovePiecePresenter implements MovePieceOutputBoundary {

    private final MovePieceViewModel movePieceViewModel;
    private ViewManagerModel viewManagerModel;

    public MovePiecePresenter(MovePieceViewModel movePieceViewModel) {
        this.movePieceViewModel = movePieceViewModel;
    }

    @Override
    public void present(MovePieceViewModel movePieceViewModel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'present'");
    }
}
