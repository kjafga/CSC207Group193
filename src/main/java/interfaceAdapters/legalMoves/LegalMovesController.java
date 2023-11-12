package interfaceAdapters.legalMoves;

import useCase.legalMoves.LegalMovesInputBoundry;
import useCase.legalMoves.LegalMovesInputData;

public class LegalMovesController {
    final LegalMovesInputBoundry legalMovesInputInteractor;

    public LegalMovesController(LegalMovesInputBoundry legalMovesInputInteractor) {
        this.legalMovesInputInteractor = legalMovesInputInteractor;
    }

    public void execute(int position){
        LegalMovesInputData legalMovesInputData = new LegalMovesInputData(position);
        legalMovesInputInteractor.execute(legalMovesInputData);
    }
}
