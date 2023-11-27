package view;

import interfaceAdapters.sendBoardToApi.SendBoardToApiController;
import interfaceAdapters.sendBoardToApi.SendBoardToApiState;
import interfaceAdapters.sendBoardToApi.SendBoardToApiViewModel;
import interfaceAdapters.legalMoves.LegalMovesController;
import interfaceAdapters.legalMoves.LegalMovesState;
import interfaceAdapters.legalMoves.LegalMovesViewModel;
import interfaceAdapters.movePiece.MovePieceController;
import interfaceAdapters.movePiece.MovePieceState;
import interfaceAdapters.movePiece.MovePieceViewModel;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public class BoardView implements PropertyChangeListener {

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
    private int onSquare = -1;
    private int promoSquare = -1;
    private List<Integer> legalMoves = emptyList();

    public BoardView(LegalMovesViewModel legalMovesViewModel, MovePieceViewModel movePieceViewModel, SendBoardToApiViewModel sendBoardToApiViewModel,
                     LegalMovesController legalMovesController, MovePieceController movePieceController, SendBoardToApiController sendBoardToApiController) {
        this.legalMovesController = legalMovesController;
        this.movePieceController = movePieceController;
        this.sendBoardToApiController = sendBoardToApiController;

        legalMovesViewModel.addPropertyChangeListener(this);
        movePieceViewModel.addPropertyChangeListener(this);
        sendBoardToApiViewModel.addPropertyChangeListener(this);

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

    private void onSquareClicked(MouseEvent e) {
        final int clickedSquare = Integer.parseInt(((Node) e.getSource()).getId());

        if (selectedSquare == -1) {
            selectedSquare = clickedSquare;
            legalMovesController.execute(selectedSquare);
        } else if (legalMoves.contains(clickedSquare)) {
            onSquare = selectedSquare;
            promoSquare = clickedSquare;
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

    public Parent getRoot() {
        return chessBoard;
    }

    public void displayPromotionQuestion() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, "Queen", "Rook", "Knight", "Bishop");
        dialog.setTitle("Promotion");
        dialog.setHeaderText("Select piece to promote to:");

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty()) {
            return;
        }
        String piece = result.get().substring(0, 1).toLowerCase();
        movePieceController.execute(onSquare, promoSquare, piece.charAt(0));
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
                    Platform.runLater(this::displayPromotionQuestion);
                    return;
                }
                updateFromFEN(state.newBoard());
                chessBoard.setDisable(true);
                // TODO: Is this the solution we want?
                new Thread(() -> {
                    try {
                        sendBoardToApiController.execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            case "sendBoardToApiState" -> {
                SendBoardToApiState state = (SendBoardToApiState) evt.getNewValue();
                Platform.runLater(() -> {
                    updateFromFEN(state.newBoard());
                    chessBoard.setDisable(false);
                });
            }
        }
    }

}
