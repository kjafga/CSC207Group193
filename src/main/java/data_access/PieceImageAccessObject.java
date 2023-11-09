package data_access;

import java.util.Map;
import javafx.scene.image.Image;

public class PieceImageAccessObject {

    private Map<String, Image> pieceImages = new java.util.HashMap<>();

    public Map<String, Image> getPieceImages(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (Character.isLetter(string.charAt(i))) {
                if (Character.isLowerCase(string.charAt(i))) {
                    Image image = getPieceImage(String.valueOf(string.charAt(i)));
                    pieceImages.put(String.valueOf(string.charAt(i)), image);
                }
                else {
                    Image image = getPieceImage("_" + String.valueOf(string.charAt(i)));
                    pieceImages.put(String.valueOf(string.charAt(i)), image);
                }
            }
        }
        return pieceImages;
    }

    private Image getPieceImage(String piece) {
        return new Image(getClass().getResourceAsStream("/pieces/" + piece + ".png"));
    }
}
