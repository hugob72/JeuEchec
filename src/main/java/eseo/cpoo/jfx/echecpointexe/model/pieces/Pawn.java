package eseo.cpoo.jfx.echecpointexe.model.pieces;

import eseo.cpoo.jfx.echecpointexe.model.APiece;
import javafx.scene.paint.Color;

/**
 * Représente un pion dans le jeu d'échecs.
 * Le pion avance tout droit (une ou deux cases au premier coup),
 * et capture en diagonale.
 *
 * Gère également le statut de premier mouvement,
 * nécessaire pour permettre l'avancée de deux cases.
 *
 * @author Baptiste JOYEZ
 * @date Dernière modification : 10/04/2025
 */
public class Pawn extends APiece {

    private boolean firstMove = true;

    public Pawn(Color color) {
        super("pawn");
        this.setColor(color);
        String colorName = (Color.BLACK.equals(color)) ? "black" : "white";
        this.setRenderUrl("/eseo/cpoo/jfx/echecpointexe/images/pieces/setOne/Chess_pawn_" + colorName + ".png");
    }

    /**
     * Indique si le pion est encore dans son premier coup.
     *
     * @return true si le pion n’a pas encore bougé, false sinon
     */
    public boolean isFirstMove() {
        return firstMove;
    }

    /**
     * Définit si le pion est encore dans son premier coup.
     *
     * @param firstMove true si le pion est au premier coup, false sinon
     */
    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }

    /**
     * Met à jour le statut de premier déplacement du pion.
     */
    @Override
    public void markAsMoved() {
        firstMove = false;
    }

    /**
     * Calcule les déplacements possibles du pion, y compris les captures en diagonale.
     *
     * @param board Le plateau de jeu actuel
     */
    @Override
    public void calculateDestinations(APiece[][] board) {
        int x = getX();
        int y = getY();
        int dir = getColor().equals(Color.WHITE) ? -1 : 1;

        // Avancer d'une case
        if (isFree(x, y + dir, board)) {
            addDestination(x, y + dir);

            // Deux cases au premier tour
            if (firstMove && isFree(x, y + 2 * dir, board)) {
                addDestination(x, y + 2 * dir);
            }
        }

        // Capture diagonale gauche
        if (canCapture(x - 1, y + dir, board)) {
            addDestination(x - 1, y + dir);
        }

        // Capture diagonale droite
        if (canCapture(x + 1, y + dir, board)) {
            addDestination(x + 1, y + dir);
        }
    }
}
