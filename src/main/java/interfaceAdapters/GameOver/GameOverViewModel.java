package interfaceAdapters.GameOver;

import interfaceAdapters.ViewModel;
import interfaceAdapters.movePiece.MovePieceState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GameOverViewModel extends ViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private GameOverState state = null;
    public GameOverViewModel() {
        super("Game Over");
    }

    void setState(GameOverState state) {
        this.state = state;
    }
    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("gameOverState", null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }
}
