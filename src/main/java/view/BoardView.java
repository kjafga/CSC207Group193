package view;

import interfaceAdapters.GameOver.GameOverState;
import interfaceAdapters.GameOver.GameOverViewModel;
import interfaceAdapters.newGame.NewGameController;
import interfaceAdapters.newGame.NewGameState;
import interfaceAdapters.sendBoardToApi.SendBoardToApiController;
import interfaceAdapters.sendBoardToApi.SendBoardToApiState;
import interfaceAdapters.sendBoardToApi.SendBoardToApiViewModel;
import interfaceAdapters.legalMoves.LegalMovesController;
import interfaceAdapters.legalMoves.LegalMovesState;
import interfaceAdapters.legalMoves.LegalMovesViewModel;
import interfaceAdapters.movePiece.MovePieceController;
import interfaceAdapters.movePiece.MovePieceState;
import interfaceAdapters.movePiece.MovePieceViewModel;
import interfaceAdapters.sendBoardToApi.SendBoardToApiController;
import interfaceAdapters.sendBoardToApi.SendBoardToApiState;
import interfaceAdapters.sendBoardToApi.SendBoardToApiViewModel;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;

import java.beans.EventHandler;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public class BoardView implements PropertyChangeListener {
    private  final String name = "BoardView";
    private static final Background LIGHT = new Background(new BackgroundFill(Color.web("#F0D9B5"), null, null));
    private static final Background DARK = new Background(new BackgroundFill(Color.web("#B58863"), null, null));

    private static final HashMap<Character, Image> IMAGES = new HashMap<>(12);

    static {
        for (char c : new char[]{'k', 'q', 'r', 'b', 'n', 'p'}) {
            InputStream whiteFile = BoardView.class.getResourceAsStream("/pieces/_" + c + ".png");
            InputStream blackFile = BoardView.class.getResourceAsStream("/pieces/" + c + ".png");
            assert whiteFile != null && blackFile != null;
            IMAGES.put(Character.toUpperCase(c), new Image(whiteFile));
            IMAGES.put(c, new Image(blackFile));
        }
    }

    private final GridPane chessBoard = new GridPane();

    private final LegalMovesController legalMovesController;
    private final MovePieceController movePieceController;
    private final SendBoardToApiController sendBoardToApiController;

    private int selectedSquare = -1;
    private int clickedSquare = -1;
    private List<Integer> legalMoves = emptyList();


    public String getViewName(){
        return this.name;
    }

    public BoardView(LegalMovesViewModel legalMovesViewModel, MovePieceViewModel movePieceViewModel, SendBoardToApiViewModel sendBoardToApiViewModel,
                     LegalMovesController legalMovesController, MovePieceController movePieceController, SendBoardToApiController sendBoardToApiController,
                     GameOverViewModel gameOverViewModel) {
        this.legalMovesController = legalMovesController;
        this.movePieceController = movePieceController;
        this.sendBoardToApiController = sendBoardToApiController;

        legalMovesViewModel.addPropertyChangeListener(this);
        movePieceViewModel.addPropertyChangeListener(this);
        sendBoardToApiViewModel.addPropertyChangeListener(this);
        gameOverViewModel.addPropertyChangeListener(this);

        for (int i = 0; i < 64; ++i) {
            Pane square = new Pane();
            square.setPrefSize(100, 100);
            square.setId(String.valueOf(i));
            square.setBackground(((i ^ i >> 3) & 1) != 0 ? LIGHT : DARK);
            square.setOnMouseClicked(this::onSquareClicked);
            GridPane.setConstraints(square, i & 7, 7 - (i >> 3));
            chessBoard.getChildren().add(square);
        }
        updateFromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    }

    public Parent getRoot() {
        return chessBoard;
    }

    private void onSquareClicked(MouseEvent e) {
        clickedSquare = Integer.parseInt(((Node) e.getSource()).getId());

        if (selectedSquare == -1) {
            selectedSquare = clickedSquare;
            legalMovesController.execute(selectedSquare);
        } else if (legalMoves.contains(clickedSquare)) {
            movePieceController.execute(selectedSquare, clickedSquare, '?');
            selectedSquare = -1;
            legalMoves = emptyList();
        } else {
            for (int move : legalMoves) {
                Pane square = (Pane) chessBoard.getChildren().get(move);
                square.getChildren().removeLast();
            }
            if (clickedSquare == selectedSquare) {
                selectedSquare = -1;
                legalMoves = emptyList();
            } else {
                selectedSquare = clickedSquare;
                legalMovesController.execute(selectedSquare);
            }
        }
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
                assert IMAGES.containsKey(c);
                ImageView view = new ImageView();
                view.setImage(IMAGES.get(c));
                view.setFitWidth(100);
                view.setFitHeight(100);

                List<Node> squareChildren = ((Pane) squares.get(i)).getChildren();
                squareChildren.clear();
                squareChildren.add(view);

                ++i;
            }
        }
    }

    private void displayPromotionQuestion(int oldSquare, int newSquare){
        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, "Queen", "Rook", "Knight", "Bishop");
        dialog.setTitle("Promotion");
        dialog.setHeaderText("Select piece to promote to:");

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty()) {
            return;
        }
        if (result.get().equals("Knight")) {
            result = Optional.of("N");
        }
        char piece = Character.toLowerCase(result.get().charAt(0));
        System.out.println(oldSquare +" - "+ newSquare +" - "+ piece);
        movePieceController.execute(oldSquare, newSquare, piece);
    }


    private void displayGameoverScreen(String reason) {
        Alert gameOverPopup = new Alert(Alert.AlertType.INFORMATION);
        gameOverPopup.setTitle("Game Over");
        gameOverPopup.setHeaderText("The game has ended in " + reason + ".");
        gameOverPopup.setContentText("Push OK to return to the main menu");

        Optional<ButtonType> result = gameOverPopup.showAndWait();
    }

    private void returnToMainMenuClicked(ActionEvent e) {
        //this.returnToMainMenuController.execute();
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {


        switch (evt.getPropertyName()) {
            case "legalState" -> {
                legalMoves = ((LegalMovesState) evt.getNewValue()).legalMoves();
                if (legalMoves.isEmpty()) {
                    selectedSquare = -1;
                    legalMoves = emptyList();
                }
                for (int move : legalMoves) {
                    Pane square = (Pane) chessBoard.getChildren().get(move);
                    square.getChildren().add(new Circle(50, 50, 10, Color.GREEN));
                }
            }
            case "moveState" -> {
                MovePieceState state = (MovePieceState) evt.getNewValue();
                if (state.newBoard().equals("promotionQuestion")) {
                    System.out.println(selectedSquare +" - "+ clickedSquare +" - ");

                    displayPromotionQuestion(selectedSquare, clickedSquare);
                    return;
                }

                Platform.runLater(() -> {
                    updateFromFEN(state.newBoard());
                    if (state.waitForApiMove()) {
                        chessBoard.setDisable(true);
                    }
                });

            }
            case "sendBoardToApiState" -> {
                SendBoardToApiState state = (SendBoardToApiState) evt.getNewValue();
                Platform.runLater(() -> {
                    updateFromFEN(state.newBoard());
                    chessBoard.setDisable(false);
                });
            }
            case "newGame" -> {
                NewGameState state = (NewGameState) evt.getNewValue();
                Platform.runLater(() -> {
                    updateFromFEN(state.getNewBoard());
                });
            }
            case "gameOverState" -> {
                String reason = ((GameOverState) evt.getNewValue()).gameOverMessage();
                Platform.runLater(() -> {
                    displayGameoverScreen(reason);
                });
            }
        }

    }

}
