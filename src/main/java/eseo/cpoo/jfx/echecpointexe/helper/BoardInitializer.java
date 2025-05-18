package eseo.cpoo.jfx.echecpointexe.helper;

import eseo.cpoo.jfx.echecpointexe.factory.PieceFactory;
import eseo.cpoo.jfx.echecpointexe.model.APiece;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.function.BiConsumer;

/**
 * Utility class for initializing the chessboard with all the starting pieces.
 */
public final class BoardInitializer {

    private BoardInitializer() {
        // Utility class should not be instantiated
    }

    /**
     * Places all initial pieces (back rows and pawns) on the chessboard.
     *
     * @param board the logical 2D array representing the board
     * @param chessGrid the JavaFX GridPane used to display the board
     * @param setPieceGraphic method reference to apply piece graphics to UI buttons
     */
    public static void placeAllPiecesInitialisation(APiece[][] board, GridPane chessGrid,
                                                    BiConsumer<APiece, Button> setPieceGraphic) {
        placeBackRow(board, 0, true, chessGrid, setPieceGraphic);
        placePawns(board, 1, true, chessGrid, setPieceGraphic);
        placeBackRow(board, 7, false, chessGrid, setPieceGraphic);
        placePawns(board, 6, false, chessGrid, setPieceGraphic);
    }

    public static void placeAllPieces(APiece[][] board, GridPane chessGrid,
                                                    BiConsumer<APiece, Button> setPieceGraphic) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                APiece piece = board[row][col];
                if (piece != null) {
                    Button btn = GridManager.getButtonAt(chessGrid, col, row);
                    setPieceGraphic.accept(board[row][col], btn);
                }
            }
        }
    }

    /**
     * Places the back row pieces (major pieces) for a specific color.
     *
     * @param board the board model
     * @param row the row index
     * @param isBlack true if placing black pieces
     * @param chessGrid the JavaFX board
     * @param setPieceGraphic method to apply piece graphics
     */
    private static void placeBackRow(APiece[][] board, int row, boolean isBlack,
                                     GridPane chessGrid,
                                     BiConsumer<APiece, Button> setPieceGraphic) {
        Color color = isBlack ? Color.BLACK : Color.WHITE;
        String[] types = {"Rook", "Knight", "Bishop", "Queen", "King", "Bishop", "Knight", "Rook"};

        for (int col = 0; col < 8; col++) {
            APiece piece = PieceFactory.create(types[col], color);
            piece.setPosition(new Point2D(col, row));
            board[row][col] = piece;
            Button btn = GridManager.getButtonAt(chessGrid, col, row);
            setPieceGraphic.accept(piece, btn);
        }
    }

    /**
     * Places pawns for a specific color.
     *
     * @param board the board model
     * @param row the row index
     * @param isBlack true if placing black pawns
     * @param chessGrid the JavaFX board
     * @param setPieceGraphic method to apply piece graphics
     */
    private static void placePawns(APiece[][] board, int row, boolean isBlack,
                                   GridPane chessGrid,
                                   BiConsumer<APiece, Button> setPieceGraphic) {
        Color color = isBlack ? Color.BLACK : Color.WHITE;
        for (int col = 0; col < 8; col++) {
            APiece pawn = PieceFactory.create("Pawn", color);
            pawn.setPosition(new Point2D(col, row));
            board[row][col] = pawn;
            Button btn = GridManager.getButtonAt(chessGrid, col, row);
            setPieceGraphic.accept(pawn, btn);
        }
    }
}
