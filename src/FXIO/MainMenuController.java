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
        try {
            //cleanup();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("PlayerSearch.fxml"));
            Parent root = loader.load();

            // Loading the controller
            PlayerSearchController controller = loader.getController();
            controller.setMain(main);

            // Set the primary stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Player Search");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ClubSearchButtonPressed(ActionEvent actionEvent) throws IOException {
        try {
            //cleanup();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ClubSearch.fxml"));
            Parent root = loader.load();

            // Loading the controller
            ClubSearch controller = loader.getController();
            controller.setMain(main);

            // Set the primary stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Club Search");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void AddPlayerButtonPressed(ActionEvent actionEvent) throws IOException {
        try {
            //cleanup();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AddPlayer.fxml"));
            Parent root = loader.load();

            // Loading the controller
            AddPlayer controller = loader.getController();
            controller.setMain(main);

            // Set the primary stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Add Player");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

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