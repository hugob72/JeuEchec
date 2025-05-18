package eseo.cpoo.jfx.echecpointexe.model.pieces;

import eseo.cpoo.jfx.echecpointexe.model.APiece;
import javafx.scene.paint.Color;

/**
 * @brief Représente le roi dans le jeu d'échecs.
 * Le roi se déplace d'une case dans toutes les directions.
 * Il ne peut pas sauter ni se déplacer dans une case occupée par une pièce alliée.
 *
 * @author Baptiste JOYEZ
 * @date Dernière modification : 10/04/2025
 */
public class King extends APiece {

    public King(Color color) {
        super("king");
        this.setColor(color);
        String colorName = (Color.BLACK.equals(color)) ? "black" : "white";
        this.setRenderUrl("/eseo/cpoo/jfx/echecpointexe/images/pieces/setOne/Chess_king_" + colorName + ".png");
    }

    /**
     * Calcule les destinations valides du roi en fonction de sa position.
     *
     * @param board Le plateau de jeu actuel.
     */
    @Override
    public void calculateDestinations(APiece[][] board) {
        int x = getX();
        int y = getY();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0){ continue;}
                int nx = x + dx;
                int ny = y + dy;
                if (isFree(nx, ny, board) || canCapture(nx, ny, board)) {
                    addDestination(nx, ny);
                }
            }
        }
    }
}
