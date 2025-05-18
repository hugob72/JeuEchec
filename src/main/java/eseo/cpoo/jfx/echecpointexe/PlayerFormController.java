package eseo.cpoo.jfx.echecpointexe;

import eseo.cpoo.jfx.echecpointexe.model.player.Player;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PlayerFormController {

    @FXML
    private TextField nicknameTextField;

    private Player createdPlayer;

    public Player getCreatedPlayer() {
        return createdPlayer;
    }

    /** Création du nouveau joueur et fermeture de la modal */
    @FXML
    public void onCreateButtonClicked() {
        createdPlayer = new Player(nicknameTextField.getText());
        Stage stage = (Stage) nicknameTextField.getScene().getWindow();
        stage.close();
    }

}
