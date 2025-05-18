package eseo.cpoo.jfx.echecpointexe.model.pieces;

import eseo.cpoo.jfx.echecpointexe.model.APiece;
import javafx.scene.paint.Color;

/**
 * Représente la reine dans le jeu d'échecs.
 * Elle combine les mouvements de la tour et du fou :
 * déplacements en ligne droite et en diagonale.
 *
 * C’est la pièce la plus polyvalente sur l’échiquier.
 *
 * @author Baptiste JOYEZ
 * @date Dernière modification : 10/04/2025
 */
public class Queen extends APiece {

    public Queen(Color color) {
        super("queen");
        this.setColor(color);
        String colorName = (Color.BLACK.equals(color)) ? "black" : "white";
        this.setRenderUrl("/eseo/cpoo/jfx/echecpointexe/images/pieces/setOne/Chess_queen_" + colorName + ".png");
    }

    /**
     * Calcule les déplacements valides de la reine.
     * Elle combine les mouvements de la tour et du fou.
     *
     * @param board Le plateau de jeu actuel.
     */
    @Override
    public void calculateDestinations(APiece[][] board) {
        // Tour
        addLineMoves(board, 1, 0);
        addLineMoves(board, -1, 0);
        addLineMoves(board, 0, 1);
        addLineMoves(board, 0, -1);

        // Fou
        addLineMoves(board, 1, 1);
        addLineMoves(board, -1, 1);
        addLineMoves(board, 1, -1);
        addLineMoves(board, -1, -1);
    }
}
