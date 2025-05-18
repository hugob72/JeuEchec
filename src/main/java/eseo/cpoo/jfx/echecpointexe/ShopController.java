package eseo.cpoo.jfx.echecpointexe;


import eseo.cpoo.jfx.echecpointexe.BoardAction.TurnManager;
import eseo.cpoo.jfx.echecpointexe.helper.FilterType;
import eseo.cpoo.jfx.echecpointexe.helper.ShopManager;
import eseo.cpoo.jfx.echecpointexe.model.article.Avatar;
import eseo.cpoo.jfx.echecpointexe.model.article.PieceDesign;
import eseo.cpoo.jfx.echecpointexe.model.article.Template;
import eseo.cpoo.jfx.echecpointexe.model.player.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ShopController {

    @FXML
    private GridPane shopGrid;

    @FXML
    private Text namePlayerOneText;

    @FXML
    private Text coinsPlayerOneText;

    @FXML
    private Text namePlayerTwoText;

    @FXML
    private Text coinsPlayerTwoText;

    @FXML
    private Button templateButton;

    @FXML
    private Button pieceButton;

    @FXML
    private Button avatarButton;

    @FXML
    private VBox confirmVbox;

    @FXML
    private Text confirmText;

    @FXML
    private Button playerOneButton;

    @FXML
    private Button playerTwoButton;

    @FXML
    private Button backButton;

    private TurnManager turnManager;

    private ShopManager shopManager;

    private FilterType filter;

    @FXML
    public void initialize() {
        shopManager = new ShopManager();
        shopManager.generateShopGrid(shopGrid, Template.class, this);
        confirmVbox.setVisible(false);
        updatePlayerOneDatas();
        updatePlayerTwoDatas();
    }

    @FXML
    public void onTemplateClicked(MouseEvent event) {
        filter = FilterType.TEMPLATE;
        shopGrid.getChildren().clear();
        shopManager.generateShopGrid(shopGrid, Template.class, this);
    }

    @FXML
    public void onPieceClicked(MouseEvent event) {
        filter = FilterType.PIECE_DESIGN;
        shopGrid.getChildren().clear();
        shopManager.generateShopGrid(shopGrid, PieceDesign.class, this);
    }

    @FXML
    public void onAvatarClicked(MouseEvent event) {
        filter = FilterType.AVATAR;
        shopGrid.getChildren().clear();
        shopManager.generateShopGrid(shopGrid, Avatar.class, this);
    }

    public void showConfirmBox(String article) {
        confirmText.setText("Quel joueur souhaites acheter l'article : " + article + " ?");
        confirmVbox.setVisible(true);
    }

    @FXML
    void onPlayerOneButtonClicked(ActionEvent event) {
        if (TurnManager.getPlayerOne().getCoins() >= shopManager.getSelectedArticle().getPrice()) {
            TurnManager.getPlayerOne().setCoins(TurnManager.getPlayerOne().getCoins() - shopManager.getSelectedArticle().getPrice());
            TurnManager.getPlayerOne().addArticle(shopManager.getSelectedArticle());
            updatePlayerOneDatas();
        } else {
            openAlert();
        }
    }

    @FXML
    void onPlayerTwoButtonClicked(ActionEvent event) {
        if (TurnManager.getPlayerTwo().getCoins() >= shopManager.getSelectedArticle().getPrice()) {
            TurnManager.getPlayerTwo().setCoins(TurnManager.getPlayerTwo().getCoins() - shopManager.getSelectedArticle().getPrice());
            TurnManager.getPlayerTwo().addArticle(shopManager.getSelectedArticle());
            updatePlayerTwoDatas();
        } else {
            openAlert();
        }
    }

    @FXML
    void onBackButtonClicked(ActionEvent event) {
        try {
            //Navigation
            FXMLLoader inventoryView = new FXMLLoader(getClass().getResource("/eseo/cpoo/jfx/echecpointexe/chessboard-view.fxml"));
            Parent chessboard = inventoryView.load();
            ChessBoardController controller = inventoryView.getController();
            controller.getStartedGame(turnManager);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(chessboard));
            stage.setTitle("EchecpointExe - Plateau");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void updatePlayerOneDatas() {
        namePlayerOneText.setText(TurnManager.getPlayerOne().getNickname());
        coinsPlayerOneText.setText(TurnManager.getPlayerOne().getCoins() + " pièces");
    }

    void updatePlayerTwoDatas() {
        namePlayerTwoText.setText(TurnManager.getPlayerTwo().getNickname());
        coinsPlayerTwoText.setText(TurnManager.getPlayerTwo().getCoins() + " pièces");
    }

    public void setTurnManager(TurnManager turnManager) {
        this.turnManager = turnManager;
    }

    public void openAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Monnaie insuffisante");
        alert.setHeaderText(null);
        alert.setContentText("Le joueur sélectionné n'a pas assez de pièces pour acheter cet article.");
        alert.showAndWait();
    }
}
