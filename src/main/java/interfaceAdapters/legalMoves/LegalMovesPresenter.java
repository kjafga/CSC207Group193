package interfaceAdapters.legalMoves;

import interfaceAdapters.ViewManagerModel;
import useCase.legalMoves.LegalMovesOutputBoundry;

public class LegalMovesPresenter implements LegalMovesOutputBoundry {
    private final LegalMovesViewModel legalMovesViewModel;
    private ViewManagerModel viewManagerModel;
    public LegalMovesPresenter(LegalMovesViewModel legalMovesViewModel) {
        this.legalMovesViewModel = legalMovesViewModel;
    }
}
