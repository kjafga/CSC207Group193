package view;

import app.Test;
import interfaceAdapters.Board.BoardViewModel;
import interfaceAdapters.legalMoves.LegalMovesController;
import interfaceAdapters.legalMoves.LegalMovesState;
import interfaceAdapters.legalMoves.LegalMovesViewModel;
import interfaceAdapters.movePiece.MovePieceController;
import interfaceAdapters.movePiece.MovePieceState;
import interfaceAdapters.movePiece.MovePieceViewModel;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class BoardView implements PropertyChangeListener {//implements ActionListener, PropertyChangeListener {
    private static final Background LIGHT = new Background(new BackgroundFill(Color.web("#F0D9B5"), null, null));
    private static final Background DARK = new Background(new BackgroundFill(Color.web("#B58863"), null, null));

    private static final HashMap<Character, Image> IMAGES = new HashMap<>(12);
    static {
        for (char c : new char[]{'k', 'q', 'r', 'b', 'n', 'p'}) {
            InputStream whiteFile = Test.class.getResourceAsStream("/pieces/_" + c + ".png");
            InputStream blackFile = Test.class.getResourceAsStream("/pieces/" + c + ".png");
            assert whiteFile != null && blackFile != null;
            IMAGES.put(Character.toUpperCase(c), new Image(whiteFile));
            IMAGES.put(c, new Image(blackFile));
        }
    }

    public GridPane chessBoard = new GridPane();

    public ArrayList<Pane> squares = new ArrayList<>();
    public ArrayList<ImageView> pieces = new ArrayList<>();

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

        movePieceController.setBoard(this);

        this.currentLegalMoves = new ArrayList<>();

        chessboard(this.chessBoard);





    }

    private void chessboard(GridPane chessBoard) {
        for (int i = 0; i < 64; ++i) {
            Pane square = new Pane();
            square.setPrefSize(100, 100);
            square.setId(String.valueOf(i));
            square.setBackground(((i ^ i >> 3) & 1) != 0 ? LIGHT : DARK);
            square.setOnMouseClicked(squareEventHandler);
            GridPane.setConstraints(square, i & 7, 7 - (i >> 3));
            chessBoard.getChildren().add(square);
        }
        updateFromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");

        /**
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
        }**/
    }



    EventHandler<MouseEvent> squareEventHandler = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) {
            System.out.println(String.valueOf(chessBoard.getChildren().indexOf(e.getSource())));
            int newMove = chessBoard.getChildren().indexOf(e.getSource());


            if (currentLegalMoves.contains(newMove)){
                movePieceController.execute(new int[] {recentSquare, newMove});
                currentLegalMoves = new ArrayList<>();

            }else if (newMove != recentSquare){
                for (int move : currentLegalMoves) {
                    Pane square = (Pane) chessBoard.getChildren().get(move);
                    square.getChildren().removeLast();
                }
                currentLegalMoves = new ArrayList<>();

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



    private void updateFromFEN(String fen) {
        List<Node> squares = chessBoard.getChildren();
        int i = 56;

        for (char c : fen.toCharArray()) {
            if (c == '/') {
                if ((i & 7) != 0) {
                    throw new IllegalStateException("Invalid FEN: " + fen);
                }
                i -= 16;
            } else if (c >= '1' && c <= '8') {
                for (Node square : squares.subList(i, i + c - '0')) {
                    ((Pane) square).getChildren().clear();
                }
                i += c - '0';
            } else {
                ImageView view = new ImageView();
                view.setImage(Objects.requireNonNull(IMAGES.get(c)));
                view.setFitWidth(100);
                view.setFitHeight(100);

                List<Node> squareChildren = ((Pane) squares.get(i)).getChildren();
                squareChildren.clear();
                squareChildren.add(view);

                ++i;
            }
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Property change  "+ evt.getPropertyName());

        if(evt.getPropertyName().equals("legalState")){
            LegalMovesState state = (LegalMovesState) evt.getNewValue();
            this.currentLegalMoves = state.legalMoves;
            System.out.println(currentLegalMoves);

            for (int move : currentLegalMoves) {
                Pane square = (Pane) chessBoard.getChildren().get(move);
                square.getChildren().add(new Circle(50, 50, 10, Color.GREEN));
            }
        }

        if(evt.getPropertyName().equals("moveState")){
            System.out.println("test");
            MovePieceState state = (MovePieceState)  evt.getNewValue();
            updateFromFEN(state.newBoard);
        }

    }


}
