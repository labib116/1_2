package FXIO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import java.io.IOException;
import java.util.Objects;

import static FXIO.LoginApp.playerDatabase;

public class MainMenuController {
    public Button TransfermarketButton;
    public Button PLayerSearchButton;
    public Button ClubSearchButton;
    public Button AddPlayerButton;
    public Button ExitButton;
    //public static int index=0;
    private LoginApp main;
    @FXML
    public void onHighScoreButtonClick(ActionEvent actionEvent) {
        System.out.println("High Score Button Pressed");
    }

    public void TransferMarketButtonPressed(ActionEvent actionEvent) throws IOException {
        main.showTransferMarket();
    }

    public void PlayerSearchButtonPressed(ActionEvent actionEvent) throws IOException {
        System.out.println("Player Search Button Pressed");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PlayerSearch.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Player Search");
    }

    public void ClubSearchButtonPressed(ActionEvent actionEvent) throws IOException {
        System.out.println("Club Search Button Pressed");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ClubSearch.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Club Search");

    }

    public void AddPlayerButtonPressed(ActionEvent actionEvent) throws IOException {
        System.out.println("Add Player Button Pressed");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddPlayer.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Add Player");

    }

    public void ExitButtonPressed(ActionEvent actionEvent) {
        System.out.println("Exit Button Pressed");
        playerDatabase.exit();
        System.exit(0);
    }
    public void setMain(LoginApp main) {
        this.main = main;
    }
}