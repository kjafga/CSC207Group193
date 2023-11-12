package interfaceAdapters.movePiece;

import interfaceAdapters.ViewManagerModel;
import useCase.movePiece.MovePieceInputData;
import useCase.movePiece.MovePieceOutputBoundary;
import useCase.movePiece.MovePieceOutputData;

public class MovePiecePresenter implements MovePieceOutputBoundary {

    private final MovePieceViewModel movePieceViewModel;
    private ViewManagerModel viewManagerModel;

    public MovePiecePresenter(MovePieceViewModel movePieceViewModel) {
        this.movePieceViewModel = movePieceViewModel;
    }




    @Override
    public void prepareSuccessView(MovePieceOutputData move) {

        MovePieceState state = new MovePieceState();
        state.newBoard = move.newBoard;
        movePieceViewModel.setState(state);
        movePieceViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(MovePieceOutputData move) {
        MovePieceState state = new MovePieceState();
        state.newBoard = move.newBoard;
        movePieceViewModel.setState(state);
        movePieceViewModel.firePropertyChanged();

    }
}
