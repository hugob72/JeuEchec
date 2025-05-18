package eseo.cpoo.jfx.echecpointexe;

import eseo.cpoo.jfx.echecpointexe.BoardAction.TurnManager;
import eseo.cpoo.jfx.echecpointexe.factory.PieceFactory;
import eseo.cpoo.jfx.echecpointexe.helper.BoardInitializer;
import eseo.cpoo.jfx.echecpointexe.helper.GridManager;
import eseo.cpoo.jfx.echecpointexe.helper.InventoryManager;
import eseo.cpoo.jfx.echecpointexe.helper.ShopManager;
import eseo.cpoo.jfx.echecpointexe.model.article.Article;
import eseo.cpoo.jfx.echecpointexe.model.article.Avatar;
import eseo.cpoo.jfx.echecpointexe.model.article.PieceDesign;
import eseo.cpoo.jfx.echecpointexe.model.article.Template;
import eseo.cpoo.jfx.echecpointexe.utils.ImageHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InventoryController {

    @FXML
    private HBox selectedTemplateHBox;

    @FXML
    private HBox selectedPiecesDesignHBox;

    @FXML
    private HBox selectedAvatarHBox;

    @FXML
    private GridPane playerOneGrid;

    @FXML
    private GridPane playerTwoGrid;

    @FXML
    private VBox confirmVbox;

    @FXML
    private Text confirmText;

    @FXML
    private Button yesButton;

    private TurnManager turnManager;

    private InventoryManager inventoryManager;

    @FXML
    void initialize() {
        inventoryManager = new InventoryManager();
        inventoryManager.generateInventoryGrid(playerOneGrid, TurnManager.getPlayerOne(), this);
        inventoryManager.generateInventoryGrid(playerTwoGrid, TurnManager.getPlayerTwo(), this);
    }

    @FXML
    void onYesButtonClicked(ActionEvent event) {
        // TODO
        try {
            Article preSelectedArticle = inventoryManager.getPreSelectedArticle();
            if (preSelectedArticle instanceof PieceDesign) {
                PieceDesign pieceDesign = (PieceDesign) preSelectedArticle;
                selectedPiecesDesignHBox.getChildren().clear();
                selectedPiecesDesignHBox.getChildren().add(ImageHelper.createImageView(pieceDesign.getPathRessource() + "/Chess_rook_white.png"));
                PieceFactory.changePiecesRenderUrl(turnManager.getBoard(), pieceDesign.getPathRessource() + "/Chess_");
                turnManager.setSelectedPieceDesign(pieceDesign);
            } else if (preSelectedArticle instanceof Avatar) {
                Avatar avatar = (Avatar) preSelectedArticle;
                selectedAvatarHBox.getChildren().clear();
                selectedAvatarHBox.getChildren().add(ImageHelper.createImageView(avatar.getPathRessource()));
            } else if (preSelectedArticle instanceof Template) {
                Template template = (Template) preSelectedArticle;
                selectedTemplateHBox.getChildren().clear();
                selectedTemplateHBox.getChildren().add(createTemplateColorHBox(template));
                turnManager.setSelectedTemplate(template);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public HBox createTemplateColorHBox(Template template) {
        Region leftColor = new Region();
        leftColor.setPrefSize(100, 20);
        leftColor.setStyle("-fx-background-color: " + template.getFirstColor());

        Region rightColor = new Region();
        rightColor.setPrefSize(100, 20);
        rightColor.setStyle("-fx-background-color: " + template.getSecondColor());

        HBox colorBox = new HBox(leftColor, rightColor);
        colorBox.setSpacing(2);
        colorBox.setAlignment(Pos.CENTER);
        return colorBox;
    }

    public void showConfirmBox(String article) {
        confirmText.setText("Souhaitez-vous équiper cet article : " + article + " ?");
        confirmVbox.setVisible(true);
    }

    public void setTurnManager(TurnManager turnManager) {
        this.turnManager = turnManager;
        getSelectedDesign();
    }

    public void getSelectedDesign() {
        selectedTemplateHBox.getChildren().clear();
        selectedPiecesDesignHBox.getChildren().add(ImageHelper.createImageView(turnManager.getSelectedPieceDesign().getPathRessource() + "/Chess_rook_white.png"));
        selectedTemplateHBox.getChildren().add(createTemplateColorHBox(turnManager.getSelectedTemplate()));
    }

}
