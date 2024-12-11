package FXIO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import java.io.IOException;
import java.util.Objects;

public class MainMenuController {
    @FXML
    public void onHighScoreButtonClick(ActionEvent actionEvent) {
        System.out.println("High Score Button Pressed");
    }

    public void TransferMarketButtonPressed(ActionEvent actionEvent) {
        System.out.println("Transfer Market Button Pressed");
    }

    public void PlayerSearchButtonPressed(ActionEvent actionEvent) throws IOException {
        System.out.println("Player Search Button Pressed");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PlayerSearch.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Player Search");
    }

    public void ClubSearchButtonPressed(ActionEvent actionEvent) {
        System.out.println("Club Search Button Pressed");
    }

    public void AddPlayerButtonPressed(ActionEvent actionEvent) {
        System.out.println("Add Player Button Pressed");
    }

    public void ExitButtonPressed(ActionEvent actionEvent) {
        System.out.println("Exit Button Pressed");
        System.exit(0);
    }
}