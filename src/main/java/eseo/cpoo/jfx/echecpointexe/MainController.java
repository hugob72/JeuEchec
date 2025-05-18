package eseo.cpoo.jfx.echecpointexe;

import eseo.cpoo.jfx.echecpointexe.BoardAction.TurnManager;
import eseo.cpoo.jfx.echecpointexe.model.player.League;
import eseo.cpoo.jfx.echecpointexe.model.player.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//import org.json.JSONObject;

public class MainController {

    @FXML
    private ImageView imagePlayerOne;

    @FXML
    private Text pseudoPlayerOne;

    @FXML
    private Text eloPlayerOne;

    @FXML
    private Text leaguePlayerOne;

    @FXML
    private ComboBox<Player> comboboxPlayerOne;

    @FXML
    private ImageView imagePlayerTwo;

    @FXML
    private Text pseudoPlayerTwo;

    @FXML
    private Text eloPlayerTwo;

    @FXML
    private Text leaguePlayerTwo;

    @FXML
    private ComboBox<Player> comboboxPlayerTwo;

    @FXML
    private Button createPlayerButtonOne;

    @FXML
    private Button createPlayerButtonTwo;

    private Stage addPlayerModal;

    private Player playerOne;

    private Player playerTwo;


    @FXML
    public void initialize() {
        // Liste de joueurs enregistrés sur le jeu
        List<Player> players = new ArrayList<>();
        Player playerOne = new Player("Hugo", 1500, League.GOLD, 3700, new ArrayList<>());
        Player playerTwo = new Player("Baptiste", 1750, League.DIAMOND, 410, new ArrayList<>());
        Player playerThree = new Player("Charles", 300, League.BRONZE, 140, new ArrayList<>());

        players.add(playerOne);
        players.add(playerTwo);
        players.add(playerThree);

        // Ajoute chaque joueur à la combobox
        for (Player player : players) {
            comboboxPlayerOne.getItems().add(player);
            comboboxPlayerTwo.getItems().add(player);
        }

        // Permet à la combobox d'afficher que les noms de joueur au lieu de la référence des objets
        StringConverter converter =  new StringConverter<Player>() {
            @Override
            public String toString(Player player) {
                return player != null ? player.getNickname() : "Sélectionner un joueur";
            }

            @Override
            public Player fromString(String string) {
                System.out.println("Ok3");
                return null;
            }
        };
        comboboxPlayerOne.setConverter(converter);
        comboboxPlayerTwo.setConverter(converter);

        // Met en place les champs avec des valeurs par défaut
        Image image = new Image(getClass().getResourceAsStream("/eseo/cpoo/jfx/echecpointexe/images/player/player_logo.png"));
        imagePlayerOne.setImage(image);
        pseudoPlayerOne.setText("---");
        eloPlayerOne.setText("--");
        leaguePlayerOne.setText("--");

        imagePlayerTwo.setImage(image);
        pseudoPlayerTwo.setText("---");
        eloPlayerTwo.setText("--");
        leaguePlayerTwo.setText("--");

    }

    /** Changement de joueur avec la combobox */
    @FXML
    public void onComboboxChange(ActionEvent event) {
        if (event.getSource() == comboboxPlayerOne) {
            this.playerOne = comboboxPlayerOne.getValue();
            pseudoPlayerOne.setText(this.playerOne.getNickname());
            eloPlayerOne.setText(String.valueOf(this.playerOne.getElo()));
            leaguePlayerOne.setText(String.valueOf(this.playerOne.getLeague()));
        } else if (event.getSource() == comboboxPlayerTwo) {
            this.playerTwo = comboboxPlayerTwo.getValue();
            pseudoPlayerTwo.setText(this.playerTwo.getNickname());
            eloPlayerTwo.setText(String.valueOf(this.playerTwo.getElo()));
            leaguePlayerTwo.setText(String.valueOf(this.playerTwo.getLeague()));
        }
    }

    /** Création d'un nouveau joueur via l'ouverture de la modal de création */
    @FXML
    void onCreateButtonClicked(ActionEvent event) throws IOException {
        // Configuration et ouverture de la modal de création d'un joueur
        final Stage dialog = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/eseo/cpoo/jfx/echecpointexe/player-form-view.fxml"));
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(addPlayerModal);
        dialog.setTitle("Ajouter un étudiant");
        Scene dialogScene = new Scene(fxmlLoader.load());
        dialog.setScene(dialogScene);
        dialog.showAndWait();

        // Récupérer le nouveau joueur depuis le contrôleur de la modal
        PlayerFormController controller = fxmlLoader.getController();
        Player newPlayer = controller.getCreatedPlayer();

        // Ajoute le nouveau joueur aux combobox
        comboboxPlayerOne.getItems().add(newPlayer);
        comboboxPlayerTwo.getItems().add(newPlayer);

        // On sélectionne le nouveau joueur
        if (event.getSource() == createPlayerButtonOne) {
            this.playerOne = newPlayer;
            comboboxPlayerOne.setValue(newPlayer);
            pseudoPlayerOne.setText(newPlayer.getNickname());
            eloPlayerOne.setText(String.valueOf(newPlayer.getElo()));
            leaguePlayerOne.setText(String.valueOf(newPlayer.getLeague()));
        } else if (event.getSource() == createPlayerButtonTwo) {
            this.playerTwo = newPlayer;
            comboboxPlayerTwo.setValue(newPlayer);
            pseudoPlayerTwo.setText(newPlayer.getNickname());
            eloPlayerTwo.setText(String.valueOf(newPlayer.getElo()));
            leaguePlayerTwo.setText(String.valueOf(newPlayer.getLeague()));
        }
    }

    @FXML
    void onPlayButtonClicked(ActionEvent event) {
        try {
            // Attribution des joueurs au TurnManager
            TurnManager.setPlayerOne(this.playerOne);
            TurnManager.setPlayerTwo(this.playerTwo);

            //Navigation
            Parent chessboardView = FXMLLoader.load(getClass().getResource("/eseo/cpoo/jfx/echecpointexe/chessboard-view.fxml"));
            Scene chessboardScene = new Scene(chessboardView);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(chessboardScene);
            stage.setTitle("EchecpointExe");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
