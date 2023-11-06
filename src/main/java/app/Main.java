package app;

import interfaceAdapters.ViewManagerModel;
import view.ViewManager;
import view.BoardView;
import view.View;
import interfaceAdapters.Board.BoardViewModel;

public class Main {
    public static void main(String[] args) {

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager("BoardView", viewManagerModel);

        BoardViewModel boardViewModel = new BoardViewModel();

        View.main(args);
    }
}
