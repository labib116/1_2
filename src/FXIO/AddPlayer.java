package FXIO;

import Database.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import Database.PlayerDatabase;
import javafx.stage.Stage;

import javax.lang.model.type.NullType;

import java.io.IOException;
import java.util.Objects;

import static FXIO.LoginApp.playerDatabase;

public class AddPlayer {

    @FXML
    public TextField PlayerNameField;
    @FXML
    public TextField CountryField;
    @FXML
    public TextField AgeField;
    @FXML
    public TextField HeightField;
    @FXML
    public TextField ClubField;
    @FXML
    public TextField PositionField;
    @FXML
    public TextField JerseyField;
    @FXML
    public TextField WeeklySallaryField;
    @FXML
    public Button AddButton;

    public void AddButtonPressed(ActionEvent actionEvent) {
        String playerName = PlayerNameField.getText();
        String country = CountryField.getText();
        int age = Integer.parseInt(AgeField.getText());
        double height= Double.parseDouble(HeightField.getText());
        String club = ClubField.getText();
        String position = PositionField.getText();
        String jerseyInput = JerseyField.getText();
        int jersey;

        if (jerseyInput.isEmpty()) {
            jersey = -1; // Assign 0 if input is empty
        } else {
            jersey = Integer.parseInt(jerseyInput);
        }
        long weeklySallary = Long.parseLong(WeeklySallaryField.getText());
         Player NullType = null;
        if(playerDatabase.findbyName(playerName)== NullType){

            Player p = new Player(playerName, age, country, height, club, position, jersey, (int) weeklySallary);
            playerDatabase.addPlayer(p);
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText("Player added successfully");
            successAlert.setContentText("Player " + playerName + " added successfully.");
            successAlert.showAndWait();
       }
        else{
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("An error occurred");
            errorAlert.setContentText("Could not add the player. Player already exists.");
            errorAlert.showAndWait();
        }
    }

    public void BackButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        Scene scene = new Scene(root);
        //Stage stage = (Stage) ((Event) actionEvent).getSource();
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Main Menu");
    }
}