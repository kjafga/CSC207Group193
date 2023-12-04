package useCase.returnToMainMenu;

public class ReturnToMainMenuInteractor implements ReturnToMainMenuInputBoundary {
    private final ReturnToMainMenuOutputBoundary outputBoundary;

    public ReturnToMainMenuInteractor(ReturnToMainMenuOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void returnToMainMenu() {
        outputBoundary.prepareSuccessView();
    }
}
