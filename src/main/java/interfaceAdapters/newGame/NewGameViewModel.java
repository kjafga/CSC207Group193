package interfaceAdapters.newGame;

import interfaceAdapters.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class NewGameViewModel extends ViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private NewGameState state = null;
    public void setState(NewGameState state) {
        this.state = state;
    }
    public NewGameViewModel() {
        super("New Game");
    }

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("newGame", null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    public NewGameState getstate() {
        return this.state;
    }
}
