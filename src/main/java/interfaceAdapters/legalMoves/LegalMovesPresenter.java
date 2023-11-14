package interfaceAdapters.legalMoves;

import useCase.legalMoves.LegalMovesOutputBoundary;
import useCase.legalMoves.LegalMovesOutputData;

public class LegalMovesPresenter implements LegalMovesOutputBoundary {

    private final LegalMovesViewModel legalMovesViewModel;

    public LegalMovesPresenter(LegalMovesViewModel legalMovesViewModel) {
        this.legalMovesViewModel = legalMovesViewModel;
    }

    @Override
    public void prepareSuccessView(LegalMovesOutputData outputData) {
        LegalMovesState state = new LegalMovesState(outputData.legalMoves());
        legalMovesViewModel.setState(state);
        legalMovesViewModel.firePropertyChanged();
    }

}
