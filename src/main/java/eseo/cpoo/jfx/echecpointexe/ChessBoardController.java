package eseo.cpoo.jfx.echecpointexe;

import eseo.cpoo.jfx.echecpointexe.model.APiece;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import eseo.cpoo.jfx.echecpointexe.BoardAction.TurnManager;
import eseo.cpoo.jfx.echecpointexe.BoardAction.KingDetector;
import eseo.cpoo.jfx.echecpointexe.utils.ImageHelper;
import eseo.cpoo.jfx.echecpointexe.helper.BoardInitializer;
import eseo.cpoo.jfx.echecpointexe.helper.GridManager;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Contrôleur principal pour la gestion de l'échiquier dans l'application JavaFX.
 * Gère l'initialisation du plateau, les interactions utilisateur, le placement et
 * les déplacements des pièces, ainsi que la détection des situations de jeu comme
 * l'échec et l'échec et mat.
 */
public class ChessBoardController {

    @FXML
    GridPane chessGrid; // Grille représentant le plateau d'échecs
    @FXML
    TextArea logBox;    // Zone d'affichage des logs
    @FXML
    Label statusLabel;  // Indicateur du joueur actif
    @FXML
    private Button shopButton;
    @FXML
    private Button inventoryButton;
    @FXML
    private Button rankButton;
    @FXML
    private Button exitButton;

    private TurnManager turnManager;

    APiece selectedAPiece;
    Button selectedButton;
    List<Circle> possibilities = new ArrayList<>();

    /**
     * Initialisation de la grille par les helper (compartimentation des méthodes et object à générer)
     * Affiche un log de fin d'initialisation
     */
    @FXML
    public void initialize() {
        // Lorsqu'il s'agit d'une nouvelle partie
        if (turnManager == null) {
            turnManager = new TurnManager();
            GridManager.generateChessGrid(chessGrid, turnManager.getSelectedTemplate().getFirstColor(), turnManager.getSelectedTemplate().getSecondColor());
            BoardInitializer.placeAllPiecesInitialisation(turnManager.getBoard(), chessGrid, this::setAPieceOnSquare);
        }

//        GridManager.generateChessGrid(chessGrid);
//        BoardInitializer.placeAllPieces(turnManager.getBoard(), chessGrid, this::setAPieceOnSquare);
        addClickHandlers();
        updateStatus();

        ImageHelper.setImageToButton(new Image(getClass().getResourceAsStream("/eseo/cpoo/jfx/echecpointexe/images/icon/shop.png")), shopButton);
        ImageHelper.setImageToButton(new Image(getClass().getResourceAsStream("/eseo/cpoo/jfx/echecpointexe/images/icon/rules.png")), inventoryButton);
        ImageHelper.setImageToButton(new Image(getClass().getResourceAsStream("/eseo/cpoo/jfx/echecpointexe/images/icon/trophy.png")), rankButton);
        ImageHelper.setImageToButton(new Image(getClass().getResourceAsStream("/eseo/cpoo/jfx/echecpointexe/images/icon/exit.png")), exitButton);

        log("0 Initialisation terminée");
    }

    @FXML
    void onShopButtonClicked(ActionEvent event) {
        try {
            //Navigation
            FXMLLoader shopView = new FXMLLoader(getClass().getResource("/eseo/cpoo/jfx/echecpointexe/shop-view.fxml"));
            Parent shop = shopView.load();
            ShopController controller = shopView.getController();
            controller.setTurnManager(turnManager);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(shop));
            stage.setTitle("EchecpointExe - Boutique");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onInventoryButtonClicked(ActionEvent event) {
        try {
            //Navigation
            FXMLLoader inventoryView = new FXMLLoader(getClass().getResource("/eseo/cpoo/jfx/echecpointexe/inventory-view.fxml"));
            Parent inventory = inventoryView.load();
            InventoryController controller = inventoryView.getController();
            controller.setTurnManager(turnManager);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(inventory));
            stage.setTitle("EchecpointExe - Inventaire");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateStatus() {
        String colorName = turnManager.getCurrentPlayer().equals(Color.WHITE) ? "Blanc" : "Noir";
        statusLabel.setText("<=\u003e Tour du joueur : " + colorName);
    }

    private Button getButtonAt(int col, int row) {
        return GridManager.getButtonAt(chessGrid, col, row);
    }

    private Circle getCircleAt(int col, int row) {
        return GridManager.getCircleAt(chessGrid, col, row);
    }

    private void setAPieceOnSquare(APiece piece, Button button) {
        if (button != null && piece.getRenderUrl() != null) {
            try {
                button.setGraphic(ImageHelper.createImageView(piece.getRenderUrl()));
            } catch (IllegalArgumentException | NullPointerException e) {
                log("X Erreur image : " + e.getMessage());
            }
        }
    }

    private void clearSquare(Button button) {
        if (button != null) {
            button.setGraphic(null);
        }
    }

    private void addClickHandlers() {
        for (Node node : chessGrid.getChildren()) {
            if (node instanceof StackPane stackPane) {
                for (Node child : stackPane.getChildren()) {
                    if (child instanceof Button button) {
                        button.setOnAction(e -> {
                            int col = GridPane.getColumnIndex(stackPane) != null ? GridPane.getColumnIndex(stackPane) : 0;
                            int row = GridPane.getRowIndex(stackPane) != null ? GridPane.getRowIndex(stackPane) : 0;
                            handleClick(col, row, button);
                        });
                    }
                }
            }

        }
    }

    /**
     * Gère un clic utilisateur sur l'échiquier.
     * @param col colonne cliquée
     * @param row ligne cliquée
     * @param button bouton cliqué
     */
    void handleClick(int col, int row, Button button) {
        if (selectedAPiece == null) {
            handleSelection(col, row, button);
        } else {
            handleMove(col, row, button);
        }
    }

    /**
     * Traite la sélection d'une pièce sur la case donnée.
     * @param col colonne sélectionnée
     * @param row ligne sélectionnée
     * @param button bouton de la case
     */
    private void handleSelection(int col, int row, Button button) {
        APiece clickedPiece = turnManager.getBoard()[row][col];
        if (clickedPiece != null && clickedPiece.getColor().equals(turnManager.getCurrentPlayer())) {
            selectedAPiece = clickedPiece;
            selectedButton = button;
            selectedAPiece.findValidDestinations(turnManager.getBoard());
            selectedAPiece.getValidDestinations().forEach(p -> {
                        log("✔️ " + selectedAPiece.getType() + " peut aller en : " + (int) p.getX() + ", " + (int) p.getY());
                        Circle destination = getCircleAt((int) p.getX(), (int) p.getY());
                        possibilities.add(destination);
                        destination.setVisible(true);
                    }
            );
        } else {
            log("! Ce n'est pas à cette couleur de jouer !");
        }
    }

    private void handleMove(int col, int row, Button button) {
        selectedAPiece.findValidDestinations(turnManager.getBoard());
        boolean isValidMove = selectedAPiece.getValidDestinations().stream()
                .anyMatch(p -> (int) p.getX() == col && (int) p.getY() == row);

        if (isInCheckAfterMove(col, row)) {
            log("⛔ Ce coup ne résout pas l'échec !");
            clearSelection();
            return;
        }

        if (isValidMove) {
            performMove(col, row, button);
        } else {
            log("! Mouvement non autorisé !");
        }

        clearSelection();
    }

    private boolean isInCheckAfterMove(int col, int row) {
        Color currentPlayer = turnManager.getCurrentPlayer();
        if (!KingDetector.isCheck(turnManager.getBoard(), currentPlayer)) {return false;}

        Point2D from = selectedAPiece.getPosition();
        APiece backup = turnManager.getBoard()[row][col];

        turnManager.getBoard()[(int) from.getY()][(int) from.getX()] = null;
        turnManager.getBoard()[row][col] = selectedAPiece;
        selectedAPiece.setPosition(new Point2D(col, row));

        boolean stillInCheck = KingDetector.isCheck(turnManager.getBoard(), currentPlayer);

        turnManager.getBoard()[(int) from.getY()][(int) from.getX()] = selectedAPiece;
        turnManager.getBoard()[row][col] = backup;
        selectedAPiece.setPosition(from);

        return stillInCheck;
    }

    private void performMove(int col, int row, Button button) {
        Point2D oldPos = selectedAPiece.getPosition();
        int startX = (int) oldPos.getX();
        int startY = (int) oldPos.getY();

        clearSquare(selectedButton);
        APiece target = turnManager.getBoard()[row][col];

        if (target != null && !target.getColor().equals(selectedAPiece.getColor())) {
            APiece.tryCapture(turnManager.getBoard(), startX, startY, col, row);
            log("Capture !");
        } else {
            turnManager.getBoard()[startY][startX] = null;
            selectedAPiece.setPosition(new Point2D(col, row));
            turnManager.getBoard()[row][col] = selectedAPiece;
            log("=> Déplacement effectué.");
        }

        setAPieceOnSquare(selectedAPiece, button);
        if (selectedAPiece.isPawn()) {
            selectedAPiece.markAsMoved();
        }

        possibilities.forEach(circle -> circle.setVisible(false));
        possibilities.clear();

        handlePostMove();
    }

    private void handlePostMove() {
        Color nextPlayer = turnManager.getNextPlayer();

        if (KingDetector.isCheckMate(turnManager.getBoard(), nextPlayer)) {
            log("💀 Échec et mat pour le joueur " + (nextPlayer.equals(Color.WHITE) ? "blanc" : "noir") + " !");
            disableBoard();
        } else if (KingDetector.isCheck(turnManager.getBoard(), nextPlayer)) {
            log("⚠️ Le roi " + (nextPlayer.equals(Color.WHITE) ? "blanc" : "noir") + " est en échec !");
        }

        turnManager.nextTurn();
        updateStatus();
    }

    private void clearSelection() {
        selectedAPiece = null;
        selectedButton = null;
    }

    void disableBoard() {
        for (Node node : chessGrid.getChildren()) {
            if (node instanceof Button button) {
                button.setDisable(true);
            }
        }
    }

    public void getStartedGame(TurnManager turnManager) {
        this.turnManager = turnManager;
        chessGrid.getChildren().clear();
        GridManager.generateChessGrid(chessGrid, turnManager.getSelectedTemplate().getFirstColor(), turnManager.getSelectedTemplate().getSecondColor());
        BoardInitializer.placeAllPieces(turnManager.getBoard(), chessGrid, this::setAPieceOnSquare);
        addClickHandlers();
    }

    private void log(String message) {
        if (logBox != null) {
            logBox.appendText(message + "\n");
        } else {
            System.out.println("LOG: " + message);
        }
    }

}
