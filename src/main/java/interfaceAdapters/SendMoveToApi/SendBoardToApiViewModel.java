package interfaceAdapters.SendMoveToApi;

import interfaceAdapters.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SendBoardToApiViewModel extends ViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);


    public SendBoardToApiViewModel() {
        super("Send Board To Api");
    }
    @Override
    public void firePropertyChanged() {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }
}
