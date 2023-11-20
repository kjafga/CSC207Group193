package interfaceAdapters.SendMoveToApi;

import useCase.legalMoves.LegalMovesOutputBoundary;
import useCase.legalMoves.LegalMovesOutputData;
import useCase.sendBoardToApi.SendBoardToApiOutputBoundary;
import useCase.sendBoardToApi.SendBoardToApiOutputData;

public class SendBoardToApiPresenter implements SendBoardToApiOutputBoundary {

    private final SendBoardToApiViewModel sendBoardToApiViewModel;

    public SendBoardToApiPresenter(SendBoardToApiViewModel sendBoardToApiViewModel) {
        this.sendBoardToApiViewModel = sendBoardToApiViewModel;
    }

    @Override
    public void prepareSuccessView(SendBoardToApiOutputData outputData) {
        SendBoardToApiState state = new SendBoardToApiState(outputData.newBoard());
        sendBoardToApiViewModel.setState(state);
        sendBoardToApiViewModel.firePropertyChanged();
    }

}
