package eseo.cpoo.jfx.echecpointexe.model.pieces;

import eseo.cpoo.jfx.echecpointexe.model.APiece;
import javafx.scene.paint.Color;
/**
 * Représente un fou dans le jeu d'échecs.
 * Le fou se déplace uniquement en diagonale, sur des cases de même couleur.
 *
 * Il ne peut pas sauter par-dessus d'autres pièces.
 * Les déplacements sont calculés dynamiquement via la logique Movable.
 *
 * @author Baptiste JOYEZ
 * @date Dernière modification : 07/04/2025
 */

public class Bishop extends APiece {

    public Bishop(Color color) {
        super("bishop");
        this.setColor(color);
        String colorName = (Color.BLACK.equals(color)) ? "black" : "white";
        this.setRenderUrl("/eseo/cpoo/jfx/echecpointexe/images/pieces/setOne/Chess_bishop_" + colorName + ".png");
    }

    @Override
    public void calculateDestinations(APiece[][] board) {
        addLineMoves(board, 1, 1);   // ↘
        addLineMoves(board, -1, 1);  // ↙
        addLineMoves(board, 1, -1);  // ↗
        addLineMoves(board, -1, -1); // ↖
    }
}
