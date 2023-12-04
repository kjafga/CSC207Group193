package interfaceAdapters.newGame;

import interfaceAdapters.ViewManagerModel;
import interfaceAdapters.movePiece.MovePieceViewModel;
import useCase.newGame.NewGameOutputBoundary;
import useCase.newGame.NewGameOutputData;

public class NewGamePresenter implements NewGameOutputBoundary {
    private final NewGameViewModel newGameViewModel;
    private final ViewManagerModel viewModelManager;
    private final MovePieceViewModel movePieceViewModel;

    public NewGamePresenter(ViewManagerModel viewModelManager, NewGameViewModel newGameViewModel,  MovePieceViewModel movePieceViewModel) {
        this.newGameViewModel = newGameViewModel;
        this.viewModelManager = viewModelManager;
        this.movePieceViewModel = movePieceViewModel;
    }

    @Override
    public void prepareSuccessView(NewGameOutputData outputData) {
        NewGameState state = new NewGameState(outputData.getNewBoard());


        this.newGameViewModel.setState(state);
        this.newGameViewModel.firePropertyChanged();

        this.viewModelManager.setActiveView("BoardView");
        this.viewModelManager.firePropertyChanged();
    }

    @Override
    public void preparefailureView(NewGameOutputData outputData) {

    }
}
