package interfaceAdapters.movePiece;

import interfaceAdapters.ViewManagerModel;
import useCase.movePiece.MovePieceInputData;
import useCase.movePiece.MovePieceOutputBoundry;
import useCase.movePiece.MovePieceOutputData;

public class MovePiecePresenter implements MovePieceOutputBoundry {

    private final MovePieceViewModel movePieceViewModel;
    private ViewManagerModel viewManagerModel;

    public MovePiecePresenter(MovePieceViewModel movePieceViewModel) {
        this.movePieceViewModel = movePieceViewModel;
    }



    @Override
    public void prepareSuccessView(MovePieceOutputData data) {

        MovePieceState state = new MovePieceState();
        state.newBoard = data.boardChange;
        movePieceViewModel.setState(state);
        movePieceViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {

    }
}
