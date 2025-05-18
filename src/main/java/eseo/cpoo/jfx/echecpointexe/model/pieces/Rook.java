package eseo.cpoo.jfx.echecpointexe.model.pieces;

import eseo.cpoo.jfx.echecpointexe.model.APiece;
import javafx.scene.paint.Color;

/**
 * Représente une tour dans le jeu d'échecs.
 * La tour peut se déplacer en ligne droite horizontalement ou verticalement,
 * tant que le chemin est libre.
 *
 * @author Baptiste JOYEZ
 * @date Dernière modification : 10/04/2025
 */
public class Rook extends APiece {

    public Rook(Color color) {
        super("rook");
        this.setColor(color);
        String colorName = (Color.BLACK.equals(color)) ? "black" : "white";
        this.setRenderUrl("/eseo/cpoo/jfx/echecpointexe/images/pieces/setOne/Chess_rook_" + colorName + ".png");
    }

    /**
     * Calcule les déplacements valides de la tour.
     * Elle se déplace uniquement en ligne droite (horizontale ou verticale).
     *
     * @param board Le plateau de jeu actuel
     */
    @Override
    public void calculateDestinations(APiece[][] board) {
        addLineMoves(board, 1, 0);   // →
        addLineMoves(board, -1, 0);  // ←
        addLineMoves(board, 0, 1);   // ↓
        addLineMoves(board, 0, -1);  // ↑
    }
}
