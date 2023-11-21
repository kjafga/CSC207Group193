package interfaceAdapters.legalMoves;

import interfaceAdapters.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LegalMovesViewModel extends ViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private LegalMovesState state = null;

    public LegalMovesViewModel() {
        super("Legal Moves");
    }

    public void setState(LegalMovesState state){
        this.state = state;
    }

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("legalState", null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

}
