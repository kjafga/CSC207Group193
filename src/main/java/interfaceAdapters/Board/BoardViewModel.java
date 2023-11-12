package interfaceAdapters.Board;

import java.beans.PropertyChangeListener;
import java.util.Map;

import interfaceAdapters.ViewModel;
import interfaceAdapters.legalMoves.LegalMovesController;
import interfaceAdapters.legalMoves.LegalMovesViewModel;
import interfaceAdapters.movePiece.MovePieceController;
import interfaceAdapters.movePiece.MovePieceViewModel;
import javafx.scene.image.Image;
import useCase.Board.BoardInputData;
import useCase.Board.BoardOutputData;

public class BoardViewModel extends ViewModel {


    public final BoardPresenter boardPresenter = new BoardPresenter(this);
    public final BoardController boardController = new BoardController(boardPresenter);

    private Map<String, Image> pieceImages;
    private String layout;

    public BoardViewModel(String start) {
        super("Board");

        prepareBoard(start);
    }

    @Override
    public void firePropertyChanged() {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addPropertyChangeListener'");
    }


    public void setPieceImages(Map<String, Image> pieceImages) {
        this.pieceImages = pieceImages;
    }

    public void setPieces(String string) {
        this.layout = string;
    }

    public Map<String, Image> getPieceImages() {
        return this.pieceImages;
    }

    public String getPieces() {
        return this.layout;
    }

    public void prepareBoard(String start) {
        BoardInputData boardInputData = new BoardInputData(start);
        BoardOutputData boardOutputData = new BoardOutputData(boardInputData.getPieces());
        boardPresenter.prepareBoard(boardOutputData);
    }
        
}