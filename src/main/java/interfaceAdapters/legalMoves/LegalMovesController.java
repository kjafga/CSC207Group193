package interfaceAdapters.legalMoves;

import useCase.legalMoves.LegalMovesInputBoundary;
import useCase.legalMoves.LegalMovesInputData;

public class LegalMovesController {

    private final LegalMovesInputBoundary legalMovesInputInteractor;

    public LegalMovesController(LegalMovesInputBoundary legalMovesInputInteractor) {
        this.legalMovesInputInteractor = legalMovesInputInteractor;
    }

    public void execute(int position){
        LegalMovesInputData legalMovesInputData = new LegalMovesInputData(position);
        legalMovesInputInteractor.execute(legalMovesInputData);
    }

}
