package eseo.cpoo.jfx.echecpointexe.helper;

import eseo.cpoo.jfx.echecpointexe.InventoryController;
import eseo.cpoo.jfx.echecpointexe.model.article.Article;
import eseo.cpoo.jfx.echecpointexe.model.article.Avatar;
import eseo.cpoo.jfx.echecpointexe.model.article.PieceDesign;
import eseo.cpoo.jfx.echecpointexe.model.article.Template;
import eseo.cpoo.jfx.echecpointexe.model.player.Player;
import eseo.cpoo.jfx.echecpointexe.utils.ImageHelper;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class InventoryManager {

    private Template selectedTemplate;

    private PieceDesign selectedPieceDesign;

    private Avatar selectedAvatar;

    private Article preSelectedArticle;

    public InventoryManager() {
    }

    public void generateInventoryGrid(GridPane inventoryPlayerGrid, Player player, InventoryController inventory) {
        int currentRow = 0;
        int currentColumn = 0;

        for (Article article: player.getArticles()) {
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(10);

            Button articleButton = new Button();
            if (article instanceof PieceDesign) {
                PieceDesign piece = (PieceDesign) article;
                ImageHelper.setImageToButton(new Image(ShopManager.class.getResourceAsStream(piece.getPathRessource() + "/Chess_rook_white.png")), articleButton);
            } else if (article instanceof Avatar) {
                Avatar avatar = (Avatar) article;
                ImageHelper.setImageToButton(new Image(ShopManager.class.getResourceAsStream(avatar.getPathRessource())), articleButton);
            } else if (article instanceof Template) {
                Template template = (Template) article;
                Region leftColor = new Region();
                leftColor.setPrefSize(20, 20);
                leftColor.setStyle("-fx-background-color: " + template.getFirstColor());

                Region rightColor = new Region();
                rightColor.setPrefSize(20, 20);
                rightColor.setStyle("-fx-background-color: " + template.getSecondColor());

                HBox colorBox = new HBox(leftColor, rightColor);
                colorBox.setSpacing(2);
                colorBox.setAlignment(Pos.CENTER);
                articleButton.setGraphic(colorBox);
            }

            articleButton.setOnAction(event -> {
                preSelectedArticle = article;
                inventory.showConfirmBox(article.getName());
            });

            Text name = new Text();
            name.setText(article.getName());
            name.setStyle("-fx-fill: white;");

            vbox.getChildren().addAll(articleButton, name);
            inventoryPlayerGrid.add(vbox, currentColumn, currentRow);

            currentColumn++;

            if (currentColumn == 4) {
                currentRow += 1;
                currentColumn = 0;
            }
            // TODO : Gestion de la grille complèe
        }
    }

    public Template getSelectedTemplate() {
        return selectedTemplate;
    }

    public void setSelectedTemplate(Template selectedTemplate) {
        this.selectedTemplate = selectedTemplate;
    }

    public PieceDesign getSelectedPieceDesign() {
        return selectedPieceDesign;
    }

    public void setSelectedPieceDesign(PieceDesign selectedPieceDesign) {
        this.selectedPieceDesign = selectedPieceDesign;
    }

    public Avatar getSelectedAvatar() {
        return selectedAvatar;
    }

    public void setSelectedAvatar(Avatar selectedAvatar) {
        this.selectedAvatar = selectedAvatar;
    }

    public Article getPreSelectedArticle() {
        return preSelectedArticle;
    }

    public void setPreSelectedArticle(Article preSelectedArticle) {
        this.preSelectedArticle = preSelectedArticle;
    }
}
