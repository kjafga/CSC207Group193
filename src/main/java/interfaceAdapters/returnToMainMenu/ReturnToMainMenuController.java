package interfaceAdapters.returnToMainMenu;

import useCase.returnToMainMenu.ReturnToMainMenuInputBoundary;

public class ReturnToMainMenuController {
    private final ReturnToMainMenuInputBoundary inputBoundary;

    public ReturnToMainMenuController(ReturnToMainMenuInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void execute() {
        inputBoundary.returnToMainMenu();
    }
}
