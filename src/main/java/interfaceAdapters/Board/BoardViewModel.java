package interfaceAdapters.Board;

import java.beans.PropertyChangeListener;

import interfaceAdapters.ViewModel;

public class BoardViewModel extends ViewModel {
    
        public BoardViewModel() {
            super("Board");
        }
    
        @Override
        public void firePropertyChanged() {
    
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener listener) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addPropertyChangeListener'");
        }
    }