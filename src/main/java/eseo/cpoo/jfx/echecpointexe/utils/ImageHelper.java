package eseo.cpoo.jfx.echecpointexe.utils;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Utilitaire pour charger et configurer les images des pièces.
 */
public final class ImageHelper {

    private ImageHelper() {
        // Classe utilitaire, non instanciable
    }

    /**
     * Crée une ImageView à partir de l'URL d'une ressource.
     *
     * @param resourceUrl L'URL de la ressource image (chemin relatif dans le classpath)
     * @return Une ImageView prête à être affichée
     * @throws IllegalArgumentException si l'image est introuvable ou invalide
     */
    public static ImageView createImageView(String resourceUrl) {
        var stream = ImageHelper.class.getResourceAsStream(resourceUrl);
        if (stream == null) {
            throw new IllegalArgumentException("Image introuvable : " + resourceUrl);
        }
        Image image = new Image(stream);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        return imageView;
    }

    public static void setImageToButton(Image image, Button button) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        button.setGraphic(imageView);
    }

}
