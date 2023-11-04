package interfaceAdapters.movePiece;

import interfaceAdapters.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MovePieceViewModel extends ViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public MovePieceViewModel() {
        super("Move Piece");
    }
    @Override
    public void firePropertyChanged() {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }
}
