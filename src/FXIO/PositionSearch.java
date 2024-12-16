package FXIO;

import Database.Player;
import Database.PlayerDatabase;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class PositionSearch {
    @FXML
    public TextField position;

    @FXML
    public TableView<Player> PositionPlayers;
    @FXML
    public TableColumn<Player, String> NameColumn;
    @FXML
    public TableColumn<Player, String> CountryColumn;
    @FXML
    public TableColumn<Player, Integer> AgeColumn;
    @FXML
    public TableColumn<Player, Double> HeightColumn;
    @FXML
    public TableColumn<Player, String> ClubColumn;
    @FXML
    public TableColumn<Player, String> PositionColumn;
    @FXML
    public TableColumn<Player, String> JerseyNumberColumn;
    @FXML
    public TableColumn<Player, Integer> WeeklySalaryColumn;
    @FXML
    public void initialize() {
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        CountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        AgeColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        HeightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        ClubColumn.setCellValueFactory(new PropertyValueFactory<>("club"));
        PositionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        JerseyNumberColumn.setCellValueFactory(cellData -> {
            Player player = cellData.getValue();
            int jerseyNumber = player.getNumber();
            return new SimpleStringProperty((jerseyNumber == -1) ? "N/A" : String.valueOf(jerseyNumber));
        });
        WeeklySalaryColumn.setCellValueFactory(new PropertyValueFactory<>("weekly_salary"));
    }


    public void SearchButtonPressed(ActionEvent actionEvent) {
        try {
            String position= this.position.getText();



            PlayerDatabase players = LoginApp.playerDatabase.findbyPosition(position);
            List<Player> answers = players.getDatabase();
            PositionPlayers.getItems().clear();
            PositionPlayers.setItems(FXCollections.observableList(answers));
        } catch (NumberFormatException e) {
            System.out.println("Invalid salary input. Please enter valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        
    }

    public void BackPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PlayerSearch.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("PlayerSearch");
    }
}