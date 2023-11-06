package view;

import interfaceAdapters.ViewManagerModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewManager implements PropertyChangeListener {
    private final String view;
    private final ViewManagerModel viewManagerModel;
    
    public ViewManager(String view, ViewManagerModel viewManagerModel) {
        this.view = view;
        this.viewManagerModel = viewManagerModel;
        this.viewManagerModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'propertyChange'");
    }
}
