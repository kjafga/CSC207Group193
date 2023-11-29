package interfaceAdapters.newGame;

import useCase.newGame.NewGameOutputBoundary;
import useCase.newGame.NewGameOutputData;

public class NewGamePresenter implements NewGameOutputBoundary {
    private final NewGameViewModel newGameViewModel;

    public NewGamePresenter(NewGameViewModel newGameViewModel) {
        this.newGameViewModel = newGameViewModel;
    }

    @Override
    public void prepareSuccessView(NewGameOutputData outputData) {
        NewGameState state = new NewGameState(outputData));
    }

    @Override
    public void preparefailureView(NewGameOutputData outputData) {

    }
}
