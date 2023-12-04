package interfaceAdapters.returnToMainMenu;

import interfaceAdapters.ViewManagerModel;
import interfaceAdapters.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ReturnToMainMenuViewModel extends ViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private ReturnToMainMenuState state = null;

    public ReturnToMainMenuViewModel() {
        super("returnToMainMenu");
    }

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("returnToMainMenu", null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }
}
