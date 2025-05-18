package eseo.cpoo.jfx.echecpointexe;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChessBoardApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Charge le fichier FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/eseo/cpoo/jfx/echecpointexe/chessboard-view.fxml"));

        // Crée la scène avec le layout chargé
        Scene scene = new Scene(fxmlLoader.load(), 900, 600); // Largeur 800 pour le HBox

        // Titre de la fenêtre
        stage.setTitle("EchecPointExe");

        // Ajoute la scène et affiche la fenêtre
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Point d'entrée principal de l'application JavaFX.
     *
     * @param args les arguments de la ligne de commande (non utilisés ici)
     */
    public static void main(String[] args) {
        launch(args);
    }

}
