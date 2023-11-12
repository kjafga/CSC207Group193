package app;

import entity.Board;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Test extends Application implements EventHandler<MouseEvent> {

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

    private final GridPane chessBoard = new GridPane();
    private Board board = new Board();
    private int selectedSquare = -1;
    private List<Integer> legalMoves = null;

    @SuppressWarnings("ExtractMethodRecommender")
    @Override
    public void start(Stage primaryStage) {
        for (int i = 0; i < 64; ++i) {
            Pane square = new Pane();
            square.setPrefSize(100, 100);
            square.setId(String.valueOf(i));
            square.setBackground(((i ^ i >> 3) & 1) != 0 ? LIGHT : DARK);
            square.setOnMouseClicked(this);
            GridPane.setConstraints(square, i & 7, 7 - (i >> 3));
            chessBoard.getChildren().add(square);
        }
        updateFromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");

        Scene scene = new Scene(chessBoard, 800, 800);
        scene.setOnKeyPressed(event -> {
            if (event.getEventType() != KeyEvent.KEY_PRESSED || event.getCode() != KeyCode.F9) {
                return;
            }

            TextInputDialog dialog = new TextInputDialog("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
            dialog.setTitle("Set FEN");
            dialog.setHeaderText("Enter the FEN:");
            dialog.getDialogPane().setPrefWidth(600);

            Optional<String> newFEN = dialog.showAndWait();
            if (newFEN.isPresent()) {
                board = new Board(newFEN.get());
                selectedSquare = -1;
                legalMoves = null;
                updateFromFEN(newFEN.get().split(" ")[0]);
            }
        });

        primaryStage.setTitle("Chessboard test: move browsing");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
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
    public void handle(MouseEvent mouseEvent) {
        Node target = (Node) mouseEvent.getTarget();
        if (target instanceof ImageView || target instanceof Circle) {
            target = target.getParent();
        }

        int targetSquare = Integer.parseInt(target.getId());

        if (selectedSquare == -1) {
            selectedSquare = targetSquare;
            legalMoves = board.getLegalMoves(selectedSquare);
            if (legalMoves.isEmpty()) {
                selectedSquare = -1;
                legalMoves = null;
                return;
            }
            for (int move : legalMoves) {
                Pane square = (Pane) chessBoard.getChildren().get(move);
                square.getChildren().add(new Circle(50, 50, 10, Color.GREEN));
            }
        } else {
            if (legalMoves.contains(targetSquare)) {
                if (!board.makeMove(selectedSquare, targetSquare, '?')) {
                    ChoiceDialog<String> dialog = new ChoiceDialog<>(null, "Queen", "Rook", "Knight", "Bishop");
                    dialog.setTitle("Promotion");
                    dialog.setHeaderText("Select piece to promote to:");

                    Optional<String> result = dialog.showAndWait();
                    if (result.isEmpty()) {
                        return;
                    }
                    board.makeMove(selectedSquare, targetSquare, result.get().charAt(0));
                }
                updateFromFEN(board.toString().split(" ")[0]);
            } else {
                for (int move : legalMoves) {
                    Pane square = (Pane) chessBoard.getChildren().get(move);
                    square.getChildren().removeLast();
                }
            }
            selectedSquare = -1;
            legalMoves = null;
        }
    }

}
