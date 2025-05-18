package eseo.cpoo.jfx.echecpointexe.BoardAction;

import eseo.cpoo.jfx.echecpointexe.model.APiece;
import eseo.cpoo.jfx.echecpointexe.model.article.PieceDesign;
import eseo.cpoo.jfx.echecpointexe.model.article.Template;
import eseo.cpoo.jfx.echecpointexe.model.player.Player;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Gère les tours de jeu entre les deux joueurs.
 * Suit l'état du joueur actif et s'il est en échec.
 */
public class TurnManager {

    private static Player playerOne;
    private static Player playerTwo;
    private static Player currentPlayer;
    private APiece[][] board = new APiece[8][8];
    private List<APiece> aPieceList = new ArrayList<>();
    private PieceDesign selectedPieceDesign;
    private Template selectedTemplate;


    private Color ACurrentPlayer;
    private boolean whiteInCheck;
    private boolean blackInCheck;

    public TurnManager() {
        this.ACurrentPlayer = Color.WHITE;
        this.whiteInCheck = false;
        this.blackInCheck = false;
        // A moi
        selectedPieceDesign = new PieceDesign("Default", 700,"/eseo/cpoo/jfx/echecpointexe/images/pieces/setOne");
        selectedTemplate = new Template("Template1", 1200,"#fdffcf","orange");
    }

    /**
     * Passe au tour du joueur suivant.
     */
    public void nextTurn() {
        ACurrentPlayer = getNextPlayer();
    }

    /**
     * Retourne le joueur dont c'est actuellement le tour.
     *
     * @return Color.WHITE ou Color.BLACK
     */
    public Color getCurrentPlayer() {
        return ACurrentPlayer;
    }

    public static Player getPlayerOne() {
        return playerOne;
    }

    public static void setPlayerOne(Player playerOne) {
        TurnManager.playerOne = playerOne;
    }

    public static Player getPlayerTwo() {
        return playerTwo;
    }

    public static void setPlayerTwo(Player playerTwo) {
        TurnManager.playerTwo = playerTwo;
    }

    public static void setCurrentPlayer(Player currentPlayer) {
        TurnManager.currentPlayer = currentPlayer;
    }

    public APiece[][] getBoard() {
        return board;
    }

    public void setBoard(APiece[][] board) {
        this.board = board;
    }

    public List<APiece> getAPieceList() {
        return aPieceList;
    }

    public void setAPieceList(List<APiece> aPieceList) {
        this.aPieceList = aPieceList;
    }

    public PieceDesign getSelectedPieceDesign() {
        return selectedPieceDesign;
    }

    public void setSelectedPieceDesign(PieceDesign selectedPieceDesign) {
        this.selectedPieceDesign = selectedPieceDesign;
    }

    public Template getSelectedTemplate() {
        return selectedTemplate;
    }

    public void setSelectedTemplate(Template selectedTemplate) {
        this.selectedTemplate = selectedTemplate;
    }

    /**
     * Retourne le joueur qui jouera après ce tour.
     *
     * @return Color opposée à currentPlayer
     */
    public Color getNextPlayer() {
        return ACurrentPlayer.equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    /**
     * Vérifie si le joueur actuellement actif est en échec.
     *
     * @return true si le joueur est en échec
     */
    public boolean isCurrentPlayerInCheck() {
        return isPlayerInCheck(ACurrentPlayer);
    }

    /**
     * Définit l’état d’échec pour un joueur.
     *
     * @param playerColor Couleur du joueur
     * @param isInCheck true si ce joueur est en échec
     */
    public void setPlayerInCheck(Color playerColor, boolean isInCheck) {
        if (playerColor.equals(Color.WHITE)) {
            whiteInCheck = isInCheck;
        } else if (playerColor.equals(Color.BLACK)) {
            blackInCheck = isInCheck;
        }
    }

    /**
     * Vérifie si un joueur spécifique est en échec.
     *
     * @param playerColor Couleur du joueur
     * @return true si ce joueur est en échec
     */
    public boolean isPlayerInCheck(Color playerColor) {
        return playerColor.equals(Color.WHITE) ? whiteInCheck : blackInCheck;
    }

    /**
     * Réinitialise le manager à son état initial.
     */
    public void reset() {
        ACurrentPlayer = Color.WHITE;
        whiteInCheck = false;
        blackInCheck = false;
    }
}
