package eseo.cpoo.jfx.echecpointexe.model.pieces;

import eseo.cpoo.jfx.echecpointexe.model.APiece;
import javafx.scene.paint.Color;

/**
 * Représente un cavalier dans le jeu d'échecs.
 * Le cavalier se déplace en \"L\" : deux cases dans une direction et une perpendiculaire.
 * Il peut sauter au-dessus d'autres pièces.
 *
 * @author Baptiste JOYEZ
 * @date Dernière modification : 10/04/2025
 */
public class Knight extends APiece {

    public Knight(Color color) {
        super("knight");
        this.setColor(color);
        String colorName = (Color.BLACK.equals(color)) ? "black" : "white";
        this.setRenderUrl("/eseo/cpoo/jfx/echecpointexe/images/pieces/setOne/Chess_knight_" + colorName + ".png");
    }

    /**
     * Calcule les destinations valides du cavalier en fonction de sa position.
     *
     * @param board Le plateau de jeu actuel.
     */
    @Override
    public void calculateDestinations(APiece[][] board) {
        int x = getX();
        int y = getY();

        int[][] moves = {
                {2, 1}, {1, 2}, {-1, 2}, {-2, 1},
                {-2, -1}, {-1, -2}, {1, -2}, {2, -1}
        };

        for (int[] move : moves) {
            int nx = x + move[0];
            int ny = y + move[1];
            if (isFree(nx, ny, board) || canCapture(nx, ny, board)) {
                addDestination(nx, ny);
            }
        }
    }
}
