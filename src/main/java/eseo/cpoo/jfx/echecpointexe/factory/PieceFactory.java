package eseo.cpoo.jfx.echecpointexe.factory;

import eseo.cpoo.jfx.echecpointexe.model.APiece;
import eseo.cpoo.jfx.echecpointexe.model.pieces.*;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * Fabrique de pièces d'échecs.
 * Permet de créer dynamiquement une pièce à partir de son nom.
 */
public final class PieceFactory {

    private PieceFactory() {
        // Constructeur privé pour empêcher l'instanciation
    }

    /**
     * Crée une pièce du type donné avec la couleur spécifiée.
     *
     * @param type Le type de la pièce (ex: "Rook", "Pawn", etc.)
     * @param color La couleur de la pièce
     * @return Une instance de la pièce demandée
     * @throws IllegalArgumentException si le type est inconnu
     */
    public static APiece create(String type, Color color) {
        return switch (type) {
            case "Rook" -> new Rook(color);
            case "Knight" -> new Knight(color);
            case "Bishop" -> new Bishop(color);
            case "Queen" -> new Queen(color);
            case "King" -> new King(color);
            case "Pawn" -> new Pawn(color);
            default -> throw new IllegalArgumentException("Type de pièce inconnu : " + type);
        };
    }

    public static void changePiecesRenderUrl(APiece[][] pieces, String url) {
        for (int row = 0; row < pieces.length; row++) {
            for (int col = 0; col < pieces[row].length; col++) {
                APiece piece = pieces[row][col];
                if (piece != null) {
                    String colorName = (Color.BLACK.equals(piece.getColor())) ? "black" : "white";
                    piece.setRenderUrl(url + piece.getType() + "_" + colorName + ".png");
                }
            }
        }
    }

}
