package eseo.cpoo.jfx.echecpointexe.BoardAction;

import eseo.cpoo.jfx.echecpointexe.model.APiece;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.List;

public final class KingDetector {

    private KingDetector() {
        // Empêche l'instanciation
    }

    public static boolean isCheck(APiece[][] board, Color kingColor) {
        Point2D kingPosition = findKing(board, kingColor);
        if (kingPosition == null) {
            System.out.println("❌ ERREUR : Le roi " + (kingColor == Color.WHITE ? "blanc" : "noir") + " est absent du plateau !");
            return true; // Roi capturé = échec technique
        }

        for (APiece[] row : board) {
            for (APiece piece : row) {
                if (piece != null && !piece.getColor().equals(kingColor)) {
                    piece.findValidDestinations(board);
                    for (Point2D target : piece.getValidDestinations()) {
                        if (target.equals(kingPosition)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean isCheckMate(APiece[][] board, Color playerColor) {
        if (!isCheck(board, playerColor)) return false;

        for (APiece[] row : board) {
            for (APiece piece : row) {
                if (piece != null && piece.getColor().equals(playerColor)) {
                    Point2D from = piece.getPosition();
                    piece.findValidDestinations(board);
                    for (Point2D to : piece.getValidDestinations()) {
                        if (wouldEscapeCheck(board, piece, from, to, playerColor)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public static boolean isStalemate(APiece[][] board, Color playerColor) {
        if (isCheck(board, playerColor)) return false;

        for (APiece[] row : board) {
            for (APiece piece : row) {
                if (piece != null && piece.getColor().equals(playerColor)) {
                    Point2D from = piece.getPosition();
                    piece.findValidDestinations(board);
                    for (Point2D to : piece.getValidDestinations()) {
                        if (wouldEscapeCheck(board, piece, from, to, playerColor)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public static boolean canCastle(APiece[][] board, Color kingColor, boolean kingSide) {
        // Placeholders: pour implémentation complète, il faut suivre l'état "hasMoved" pour roi et tour
        return false; // À implémenter selon ta gestion du roque
    }

    private static boolean wouldEscapeCheck(APiece[][] board, APiece piece, Point2D from, Point2D to, Color playerColor) {
        int fromX = (int) from.getX();
        int fromY = (int) from.getY();
        int toX = (int) to.getX();
        int toY = (int) to.getY();

        APiece backup = board[toY][toX];
        board[toY][toX] = piece;
        board[fromY][fromX] = null;
        piece.setPosition(new Point2D(toX, toY));

        boolean inCheck = isCheck(board, playerColor);

        // rollback
        board[fromY][fromX] = piece;
        board[toY][toX] = backup;
        piece.setPosition(from);

        return !inCheck;
    }

    private static Point2D findKing(APiece[][] board, Color color) {
        for (APiece[] row : board) {
            for (APiece piece : row) {
                if (piece != null && piece.getType().equalsIgnoreCase("King") && piece.getColor().equals(color)) {
                    return piece.getPosition();
                }
            }
        }
        return null;
    }
}
