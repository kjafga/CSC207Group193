package interfaceAdapters.sendBoardToApi;

import interfaceAdapters.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SendBoardToApiViewModel extends ViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private SendBoardToApiState state = null;

    public SendBoardToApiViewModel() {
        super("Send Board To Api");
    }

    public void setState(SendBoardToApiState state) {
        this.state = state;
    }

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("sendBoardToApiState", null, state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

}
