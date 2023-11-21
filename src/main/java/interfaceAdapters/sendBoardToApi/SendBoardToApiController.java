package interfaceAdapters.sendBoardToApi;

import useCase.sendBoardToApi.SendBoardToApiInputBoundary;

import java.io.IOException;

public class SendBoardToApiController {

    private final SendBoardToApiInputBoundary sendBoardToApiInputBoundary;

    public SendBoardToApiController(SendBoardToApiInputBoundary sendBoardToApiInputBoundary) {
        this.sendBoardToApiInputBoundary = sendBoardToApiInputBoundary;
    }

    public void execute() throws IOException {
        sendBoardToApiInputBoundary.execute();
    }

}
