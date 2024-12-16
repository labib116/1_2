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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ClubandCountry {

    //public TableView ClubandCountryPlayers;
    @FXML
    public TextField Club;
    @FXML
    public Button Search;
    public TableView<Player> ClubandCountryPlayers;
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
    public TextField Country;

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

    public void SearchPressed(ActionEvent actionEvent) {
        try {
            String Club= this.Club.getText();
            String Country = this.Country.getText();
            PlayerDatabase players = LoginApp.playerDatabase.findbyClubAndCountry(Club,Country);
            List<Player> answers = players.getDatabase();
            ClubandCountryPlayers.getItems().clear();
            ClubandCountryPlayers.setItems(FXCollections.observableList(answers));
        } catch (NumberFormatException e) {
            System.out.println("Invalid Input");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void BackbuttonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PlayerSearch.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("PlayerSearch");
    }
}