package interfaceAdapters.movePiece;

import useCase.movePiece.MovePieceOutputBoundary;
import useCase.movePiece.MovePieceOutputData;

public class MovePiecePresenter implements MovePieceOutputBoundary {

    private final MovePieceViewModel movePieceViewModel;

    public MovePiecePresenter(MovePieceViewModel movePieceViewModel) {
        this.movePieceViewModel = movePieceViewModel;
    }

    @Override
    public void prepareSuccessView(MovePieceOutputData outputData) {
        MovePieceState state = new MovePieceState(outputData.newBoard());
        movePieceViewModel.setState(state);
        movePieceViewModel.firePropertyChanged();
    }

    @Override
    public void preparePromotionQuestion() {
        MovePieceState state = new MovePieceState("promotionQuestion");
        movePieceViewModel.setState(state);
        movePieceViewModel.firePropertyChanged();
    }

}
