package interfaceAdapters.movePiece;

import interfaceAdapters.ViewManagerModel;
<<<<<<< HEAD
import useCase.movePiece.MovePieceOutputBoundary;
=======
import useCase.movePiece.MovePieceInputData;
import useCase.movePiece.MovePieceOutputBoundry;
import useCase.movePiece.MovePieceOutputData;
>>>>>>> main

public class MovePiecePresenter implements MovePieceOutputBoundary {

    private final MovePieceViewModel movePieceViewModel;
    private ViewManagerModel viewManagerModel;

    public MovePiecePresenter(MovePieceViewModel movePieceViewModel) {
        this.movePieceViewModel = movePieceViewModel;
    }

<<<<<<< HEAD
    @Override
    public void present(MovePieceViewModel movePieceViewModel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'present'");
=======


    @Override
    public void prepareSuccessView(MovePieceOutputData data) {

        MovePieceState state = new MovePieceState();
        state.newBoard = data.boardChange;
        movePieceViewModel.setState(state);
        movePieceViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {

>>>>>>> main
    }
}
