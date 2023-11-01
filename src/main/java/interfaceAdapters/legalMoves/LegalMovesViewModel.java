package interfaceAdapters.legalMoves;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LegalMovesViewModel extends ViewModel {



    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public LegalMovesViewModel() {
        super("Legal Moves");
    }

    @Override
    public void firePropertyChanged() {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }
}
