package interfaceAdapters.returnToMainMenu;

import interfaceAdapters.ViewManagerModel;
import useCase.returnToMainMenu.ReturnToMainMenuOutputBoundary;

public class ReturnToMainMenuPresenter implements ReturnToMainMenuOutputBoundary {

    private final ViewManagerModel viewManagerModel;

    public ReturnToMainMenuPresenter(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView() {
        System.out.println("reached the presenter");
        this.viewManagerModel.setActiveView("MainMenuView");
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void preparefailureView() {

    }
}
