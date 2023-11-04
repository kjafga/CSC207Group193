package interfaceAdapters.SendMoveToApi;

import interfaceAdapters.ViewManagerModel;
import useCase.sendBoardToApi.SendBoardToApiOutputBoundry;

public class SendBoardToApiPresenter implements SendBoardToApiOutputBoundry {

    private final SendBoardToApiViewModel sendBoardToApiViewModel;
    private ViewManagerModel viewManagerModel;

    public SendBoardToApiPresenter(SendBoardToApiViewModel sendBoardToApiViewModel) {
        this.sendBoardToApiViewModel = sendBoardToApiViewModel;
    }
}
