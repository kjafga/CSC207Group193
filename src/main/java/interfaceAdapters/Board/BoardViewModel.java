package interfaceAdapters.Board;

import java.beans.PropertyChangeListener;
import java.util.HashMap;
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

    public final MovePieceController MOVE_PIECE_CONTROLLER = new MovePieceController();
    public final MovePieceViewModel MOVE_PIECE_VIEW_MODEL = new MovePieceViewModel();
    public final LegalMovesController LEGAL_MOVES_CONTROLLER = new LegalMovesController();
    public final LegalMovesViewModel LEGAL_MOVES_VIEW_MODEL = new LegalMovesViewModel();

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

    public MovePieceViewModel getMovePieceViewModel() {
        return this.MOVE_PIECE_VIEW_MODEL;
    }

    public LegalMovesViewModel getLegalMovesViewModel() {
        return this.LEGAL_MOVES_VIEW_MODEL;
    }

    public MovePieceController getMovePieceController() {
        return this.MOVE_PIECE_CONTROLLER;
    }

    public LegalMovesController getLegalMovesController() {
        return this.LEGAL_MOVES_CONTROLLER;
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