package eseo.cpoo.jfx.echecpointexe.model;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite représentant une pièce d'échecs.
 */
public abstract class APiece {
    protected String type;
    protected Color color;
    protected Point2D position;
    protected String renderUrl;
    protected final List<Point2D> validDestinations = new ArrayList<>();

    /**
     * Calcule les déplacements possibles d'une pièce.
     * @param board L'état actuel du plateau.
     */
    public abstract void calculateDestinations(APiece[][] board);

    /**
     * Constructeur d'une pièce.
     * @param type Le type de la pièce.
     */
    public APiece(String type) {
        this.type = type;
    }

    /**
     * Récupère le type de la pièce.
     * @return le type sous forme de chaîne.
     */
    public String getType() {
        return type;
    }

    /**
     * Définit le type de la pièce.
     * @param type Type à définir.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Récupère la couleur de la pièce.
     * @return la couleur.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Définit la couleur de la pièce.
     * @param color Couleur à définir.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Récupère la position actuelle de la pièce.
     * @return un Point2D représentant la position.
     */
    public Point2D getPosition() {
        return position;
    }

    /**
     * Modifie la position de la pièce.
     * @param position nouvelle position.
     */
    public void setPosition(Point2D position) {
        this.position = position;
    }

    /**
     * Récupère l'URL de rendu de l'image.
     * @return l'URL de rendu.
     */
    public String getRenderUrl() {
        return renderUrl;
    }

    /**
     * Détermine dynamiquement l'URL de rendu selon le type et la couleur.
     */
    public void setRenderUrl(String url) {
        this.renderUrl = url;
    }

    /**
     * Retourne la coordonnée X actuelle de la pièce.
     * @return Coordonnée X.
     */
    public int getX() {
        return (int) position.getX();
    }

    /**
     * Retourne la coordonnée Y actuelle de la pièce.
     * @return Coordonnée Y.
     */
    public int getY() {
        return (int) position.getY();
    }

    /**
     * Met à jour la liste des déplacements valides.
     * @param board Le plateau de jeu.
     */
    public void findValidDestinations(APiece[][] board) {
        validDestinations.clear();
        calculateDestinations(board);
    }

    /**
     * Retourne les destinations valides pré-calculées.
     * @return Liste de positions valides.
     */
    public List<Point2D> getValidDestinations() {
        return validDestinations;
    }

    /**
     * Ajoute une destination valide si elle est dans les limites du plateau.
     * @param x Coordonnée X de la destination
     * @param y Coordonnée Y de la destination
     */
    protected void addDestination(int x, int y) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            validDestinations.add(new Point2D(x, y));
        }
    }

    /**
     * Ajoute les mouvements valides en ligne (horizontal, vertical ou diagonal).
     * @param board Plateau de jeu
     * @param dx direction X
     * @param dy direction Y
     */
    protected void addLineMoves(APiece[][] board, int dx, int dy) {
        int x = getX() + dx;
        int y = getY() + dy;

        while (inBounds(x, y)) {
            if (board[y][x] == null) {
                addDestination(x, y);
            } else {
                if (!board[y][x].getColor().equals(this.color)) {
                    addDestination(x, y);
                }
                break;
            }
            x += dx;
            y += dy;
        }
    }

    /**
     * Vérifie si une case est vide et dans les limites du plateau.
     * @param x Coordonnée X de la case
     * @param y Coordonnée Y de la case
     * @param board Plateau de jeu
     * @return true si la case est vide et dans les limites, sinon false
     */
    protected boolean isFree(int x, int y, APiece[][] board) {
        return inBounds(x, y) && board[y][x] == null;
    }

    /**
     * Vérifie si une case contient une pièce adverse.
     * @param x Coordonnée X de la case
     * @param y Coordonnée Y de la case
     * @param board Plateau de jeu
     * @return true si la case est occupée par une pièce adverse, sinon false
     */
    protected boolean canCapture(int x, int y, APiece[][] board) {
        return inBounds(x, y) &&
                board[y][x] != null &&
                !board[y][x].getColor().equals(this.color);
    }

    /**
     * Vérifie si une position est dans les limites du plateau.
     * @param x Coordonnée X à tester
     * @param y Coordonnée Y à tester
     * @return true si la position est dans les limites, sinon false
     */
    protected boolean inBounds(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    protected void calculateDestinations() {}

    /**
     * Tente de capturer une pièce ennemie à la position cible.
     *
     * @param board Plateau de jeu
     * @param fromX Position X d'origine de l'attaquant
     * @param fromY Position Y d'origine de l'attaquant
     * @param toX   Position X de la cible
     * @param toY   Position Y de la cible
     * @return true si la capture a été effectuée, false sinon
     */
    public static boolean tryCapture(APiece[][] board, int fromX, int fromY, int toX, int toY) {
        APiece attacker = board[fromY][fromX];
        APiece target = board[toY][toX];

        if (attacker == null || target == null) {
            System.out.println("❌ Attaquant ou cible manquant.");
            return false;
        }

        if (attacker.getColor().equals(target.getColor())) {
            System.out.println("❌ Impossible de capturer une pièce alliée.");
            return false;
        }

        if (target.getType().equalsIgnoreCase("king")) {
            System.out.println("❌ Tentative de capture illégale du roi !");
            return false;
        }

        // Exécution de la capture
        board[toY][toX] = attacker;
        board[fromY][fromX] = null;
        attacker.setPosition(new Point2D(toX, toY));

        System.out.println("✔️ Capture réussie de la pièce " + target.getType() + " à (" + toX + ", " + toY + ")");
        return true;
    }


    /**
     * Vérifie si cette pièce est un pion.
     * @return true si c'est un pion, false sinon
     */
    public boolean isPawn() {
        return "Pawn".equalsIgnoreCase(this.getType());
    }

    /**
     * Méthode appelée après le premier déplacement d'une pièce.
     */
    public void markAsMoved() {
        // Méthode vide par défaut, à surcharger dans Pawn
    }
}