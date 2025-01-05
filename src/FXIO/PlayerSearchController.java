package FXIO;
import Database.PlayerDatabase;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class PlayerSearchController {
   @FXML
   private Button CountrywisePlayerCountButton;
    @FXML
    private Button SallaryRangeButton;
    @FXML
    private Button PositionSearchButton;
    @FXML
    private Button ClubandCOuntrySearchButton;
    @FXML
    private Button NameSearchButton;
    @FXML
    private Button BacktoMainMenuButton;
    private LoginApp main;

    public void BacktoMainMenuButtonPressed(ActionEvent actionEvent) throws IOException {
        try {
            //cleanup();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("MainMenu.fxml"));
            Parent root = loader.load();

            // Loading the controller
            MainMenuController controller = loader.getController();
            controller.setMain(main);

            // Set the primary stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Main Menu");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void CountrywisePlayerCountButtonPressed(ActionEvent actionEvent) throws IOException {
        try {
            //cleanup();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("CountrwisePlayerCount.fxml"));
            Parent root = loader.load();

            // Loading the controller
            HashMap<String,Integer>players=LoginApp.playerDatabase.findByCountry();
            CountrwisePlayerCount controller = loader.getController();
            controller.setMain(main);
            controller.setCountryData(players);

            // Set the primary stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Countrywise Player Count");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void SallaryRangeButtonPressed(ActionEvent actionEvent) throws IOException {
        try {
            //cleanup();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SallaryRange.fxml"));
            Parent root = loader.load();

            // Loading the controller
            SallaryRange controller = loader.getController();
            controller.setMain(main);

            // Set the primary stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Sallary Range");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void PositionSearchButtonPressed(ActionEvent actionEvent) throws IOException {
        try {
            //cleanup();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("PositionSearch.fxml"));
            Parent root = loader.load();

            // Loading the controller
            PositionSearch controller = loader.getController();
            controller.setMain(main);

            // Set the primary stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Position Search");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void ClubandCOuntrySearchButtonPressed(ActionEvent actionEvent) throws IOException {
        try {
            //cleanup();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ClubandCountry.fxml"));
            Parent root = loader.load();

            // Loading the controller
            ClubandCountry controller = loader.getController();
            controller.setMain(main);

            // Set the primary stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Club and Country Search");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void NameSearchButtonPressed(ActionEvent actionEvent) throws IOException {
        try {
            //cleanup();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("NameSearch.fxml"));
            Parent root = loader.load();

            // Loading the controller
            NameSearch controller = loader.getController();
            controller.setMain(main);

            // Set the primary stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Name Search");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setMain(LoginApp main) {
        this.main=main;
    }
}