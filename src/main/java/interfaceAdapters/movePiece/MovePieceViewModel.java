package interfaceAdapters.movePiece;

import interfaceAdapters.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MovePieceViewModel extends ViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public MovePieceViewModel() {
        super("Move Piece");
    }

    public MovePieceState  state = new MovePieceState();
    public void setState (MovePieceState state){
        this.state = state;
    }

    String move;
    public void setMove(String move) {
        this.move = move;
    }

    public String getMove() {
        return move;
    }

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("moveState", null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);

    }
}
