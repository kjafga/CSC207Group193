package interfaceAdapters.legalMoves;

import interfaceAdapters.ViewManagerModel;
import useCase.legalMoves.LegalMovesOutputBoundry;
import useCase.legalMoves.LegalMovesOutputData;

public class LegalMovesPresenter implements LegalMovesOutputBoundry {
    private final LegalMovesViewModel legalMovesViewModel;
    private ViewManagerModel viewManagerModel;
    public LegalMovesPresenter(LegalMovesViewModel legalMovesViewModel) {
        this.legalMovesViewModel = legalMovesViewModel;
    }

    @Override
    public void prepareSuccessView(LegalMovesOutputData outputData) {
        LegalMovesState state = new LegalMovesState();
        state.legalMoves = outputData.legalMoves;
        legalMovesViewModel.setState(state);
        legalMovesViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {

    }
}
