package useCase.Board;

import java.util.Map;

import data_access.PieceImageAccessObject;
import javafx.scene.image.Image;

public class BoardOutputData {
    
    private String layout;
    private PieceImageAccessObject pieceImageAccessObject;
    private Map<String, Image> pieceImages;

    public BoardOutputData(String string) {
        this.layout = string;
        this.pieceImageAccessObject = new PieceImageAccessObject();
        pieceImages = pieceImageAccessObject.getPieceImages(string);
    }

    public String getPieces() {
        return layout;
    }
    
    public Map<String, Image> getPieceImages() {
        return pieceImages;
    }
}