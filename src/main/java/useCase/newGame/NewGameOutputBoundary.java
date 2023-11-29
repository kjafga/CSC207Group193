package useCase.newGame;

public interface NewGameOutputBoundary {

    void prepareSuccessView(NewGameOutputData outputData);
    void preparefailureView(NewGameOutputData outputData);
}
