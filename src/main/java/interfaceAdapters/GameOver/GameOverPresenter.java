package interfaceAdapters.GameOver;

import useCase.gameOver.GameOverOutputBoundary;
import useCase.gameOver.GameOverOutputData;

public class GameOverPresenter implements GameOverOutputBoundary {
    private final GameOverViewModel gameOverViewModel;

    public GameOverPresenter(GameOverViewModel gameOverViewModel) {
        this.gameOverViewModel = gameOverViewModel;
    }

    @Override
    public void prepareGameOverView(GameOverOutputData gameOverMessage) {

        GameOverState state = new GameOverState(gameOverMessage.gameOverMessage());
        gameOverViewModel.setState(state);
        gameOverViewModel.firePropertyChanged();
    }
}
