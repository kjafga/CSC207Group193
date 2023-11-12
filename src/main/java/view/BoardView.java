package view;

import interfaceAdapters.Board.BoardViewModel;
import interfaceAdapters.legalMoves.LegalMovesController;
import interfaceAdapters.legalMoves.LegalMovesState;
import interfaceAdapters.legalMoves.LegalMovesViewModel;
import interfaceAdapters.movePiece.MovePieceController;
import interfaceAdapters.movePiece.MovePieceViewModel;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;

public class BoardView implements PropertyChangeListener {//implements ActionListener, PropertyChangeListener {

    public TilePane chessBoard = new TilePane();

    public ArrayList<Pane> squares = new ArrayList<>();
    public ArrayList<ImageView> pieces = new ArrayList<>();
    public Map<String, Image> pieceMap = new HashMap<>();

    // View Models
    private final MovePieceViewModel movePieceViewModel;
    private final LegalMovesViewModel legalMovesViewModel;
    private final BoardViewModel boardViewModel;

    // Controllers
    private final MovePieceController movePieceController;
    private final LegalMovesController legalMovesController;

    //private EventHandler handler;

    private String move = "";

    public int recentSquare = -1;
    public List<Integer> currentLegalMoves;

    public BoardView(MovePieceViewModel movePieceViewModel, LegalMovesViewModel legalMovesViewModel, MovePieceController movePieceController, LegalMovesController legalMovesController, BoardViewModel boardViewModel) {
        this.movePieceViewModel = movePieceViewModel;
        this.legalMovesViewModel = legalMovesViewModel;
        this.movePieceController = movePieceController;
        this.legalMovesController = legalMovesController;
        this.boardViewModel = boardViewModel;

        legalMovesViewModel.addPropertyChangeListener(this);
        movePieceViewModel.addPropertyChangeListener(this);



        this.currentLegalMoves = new ArrayList<>();

        chessboard(this.chessBoard);

        pieceMap = boardViewModel.getPieceImages();

        pieceDisplay();


    }

    private void chessboard(TilePane chessBoard) {

        int val = 0;
        for (int i = 1; i < 9; i++){
            for (int j = 1; j < 9; j++){
                Pane square = new Pane();
                square.setPrefSize(100,100);
                square.setId(String.valueOf(val));
                chessBoard.getChildren().add(square);
                squares.add(square);

                ((Pane) chessBoard.getChildren().get(val)).addEventHandler(MouseEvent.MOUSE_CLICKED, squareEventHandler);

                if ((i + j) % 2 == 0) {
                    square.setBackground(new Background(new BackgroundFill(Color.web("#F0D9B5"), CornerRadii.EMPTY, Insets.EMPTY)));
                }
                else {
                    square.setBackground(new Background(new BackgroundFill(Color.web("#B58863"), CornerRadii.EMPTY, Insets.EMPTY)));
                }
                val++;
            }
        chessBoard.setTileAlignment(Pos.TOP_LEFT);
        }
    }

    public void pieceDisplay() {

        int val = 0;
        String string = getLayout();
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '/') {
                val--;
            } else if (Character.isLetter(string.charAt(i))) {
                String key = String.valueOf(string.charAt(i));
                ImageView image = new ImageView();
                image.setImage(pieceMap.get(key));
                image.setFitHeight(100);
                image.setFitWidth(100);
                ((Pane) chessBoard.getChildren().get(val)).getChildren().add(image);
            } else {
                val = val + Character.getNumericValue(string.charAt(i)) - 1;

            }
            val++;
        }
    }

    EventHandler<MouseEvent> squareEventHandler = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) {
            System.out.println(String.valueOf(chessBoard.getChildren().indexOf(e.getSource())));
            int newMove = chessBoard.getChildren().indexOf(e.getSource());


            if (currentLegalMoves.contains(newMove)){
                movePieceController.execute(new int[] {recentSquare, newMove});


            }else if (newMove != recentSquare){
                System.out.println("legalMoves");
                legalMovesController.execute(newMove);
                recentSquare = newMove;
            }
        }
    };



    public String getLayout() {
        return boardViewModel.getPieces();
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Property change  "+ evt.getPropertyName());

        if(evt.getPropertyName().equals("legalState")){
            LegalMovesState state = (LegalMovesState) evt.getNewValue();
            this.currentLegalMoves = state.legalMoves;
            System.out.println(currentLegalMoves);
        }

    }


}
