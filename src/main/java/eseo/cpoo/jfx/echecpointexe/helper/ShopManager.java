package eseo.cpoo.jfx.echecpointexe.helper;

import eseo.cpoo.jfx.echecpointexe.ShopController;
import eseo.cpoo.jfx.echecpointexe.model.article.Article;
import eseo.cpoo.jfx.echecpointexe.model.article.Avatar;
import eseo.cpoo.jfx.echecpointexe.model.article.PieceDesign;
import eseo.cpoo.jfx.echecpointexe.model.article.Template;
import eseo.cpoo.jfx.echecpointexe.utils.ImageHelper;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShopManager {

    private List<Article> articles = new ArrayList<>();

    private Article selectedArticle;

    public ShopManager() {
        buildArticles();
    }

    private void buildArticles() {
        articles.add(new Avatar("Avatar1", 150,"/eseo/cpoo/jfx/echecpointexe/images/icon/trophy.png"));
        articles.add(new Avatar("Avatar2", 300,"/eseo/cpoo/jfx/echecpointexe/images/icon/trophy.png"));
        articles.add(new PieceDesign("Design1", 700,"/eseo/cpoo/jfx/echecpointexe/images/pieces/setTwo"));
        articles.add(new PieceDesign("Design2", 700,"/eseo/cpoo/jfx/echecpointexe/images/pieces/setThree"));
        articles.add(new Template("Template1", 1200,"blue","cyan"));
        articles.add(new Template("Template2", 1200,"green","yellow"));
        articles.add(new Template("Template3", 1500,"violet","rose"));
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Article getSelectedArticle() {
        return selectedArticle;
    }

    public void setSelectedArticle(Article selectedArticle) {
        this.selectedArticle = selectedArticle;
    }

    public void generateShopGrid(GridPane shopGrid, Class<?> filter, ShopController shopController) {
        int currentRow = 0;
        int currentColumn = 0;

        List<Article> targetedArticle =  articles.stream()
                .filter(filter::isInstance)
                .toList();

        for (Article article : targetedArticle) {

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
                articleButton.setStyle("-fx-background-color: rgba(255,255,255,0.3);");
            }
            articleButton.setOnAction(event -> {
                selectedArticle = article;
                shopController.showConfirmBox(article.getName());
            });

            Text name = new Text();
            name.setText(article.getName());
            name.setStyle("-fx-fill: white;");

            Text price = new Text();
            price.setText(article.getPrice() + " pièces");
            price.setStyle("-fx-fill: white;");

            vbox.getChildren().addAll(articleButton, name, price);
            shopGrid.add(vbox, currentColumn, currentRow);

            currentColumn++;

            if (currentColumn == 4) {
                currentRow+= 1;
                currentColumn = 0;
            }

        }
    }

}
