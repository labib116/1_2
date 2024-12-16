package FXIO;
import Database.PlayerDatabase;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    public void BacktoMainMenuButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        Scene scene = new Scene(root);
        //Stage stage = (Stage) ((Event) actionEvent).getSource();
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Main Menu");


    }

    public void CountrywisePlayerCountButtonPressed(ActionEvent actionEvent) throws IOException {
        HashMap<String, Integer> players = LoginApp.playerDatabase.findByCountry();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Countrywise Player Count");
        alert.setHeaderText("Countrywise Player Count");
        StringBuilder content = new StringBuilder();
        for (String country : players.keySet()) {
            content.append(String.format("%-20s : %-10d\n", country, players.get(country)));
        }
        alert.setContentText(content.toString());
        alert.showAndWait();
    }
    public void SallaryRangeButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SallaryRange.fxml")));
        Scene scene = new Scene(root);
        //Stage stage = (Stage) ((Event) actionEvent).getSource();
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sallary Range");
    }

    public void PositionSearchButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PositionSearch.fxml")));
        Scene scene = new Scene(root);
        //Stage stage = (Stage) ((Event) actionEvent).getSource();
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Position wise Players");

    }

    public void ClubandCOuntrySearchButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ClubandCountry.fxml")));
        Scene scene = new Scene(root);
        //Stage stage = (Stage) ((Event) actionEvent).getSource();
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Club and Country wise Players");
    }

    public void NameSearchButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("NameSearch.fxml")));
        Scene scene = new Scene(root);
        //Stage stage = (Stage) ((Event) actionEvent).getSource();
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Searching PLayers by Name");

    }
}