package FXIO;

import Database.Player;
import Database.PlayerDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MaxSallary {
    @FXML
    public TextField ClubNameField;
    @FXML
    public TableColumn NameColumn;
    @FXML
    public TableColumn CountryColumn;
    @FXML
    public TableColumn AgeColumn;
    @FXML
    public TableColumn HeightColumn;
    @FXML
    public TableColumn ClubColumn;
    @FXML
    public TableColumn PositionColumn;
    @FXML
    public TableColumn JerseyNumberColumn;
    @FXML
    public TableColumn WeeklySalaryColumn;
    @FXML
    public TableView MaxSalaryPlayers;
    private LoginApp main;

    @FXML
    public void initialize() {
        // Link columns to Player properties
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        CountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        AgeColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        HeightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        ClubColumn.setCellValueFactory(new PropertyValueFactory<>("club"));
        PositionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        JerseyNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        WeeklySalaryColumn.setCellValueFactory(new PropertyValueFactory<>("weekly_salary"));
    }

    public void SearchButtonPressed(ActionEvent actionEvent) {
        String ClubName = ClubNameField.getText();
        PlayerDatabase max_sallary=LoginApp.playerDatabase.find_max_sallary_players(ClubName);
        List<Player>MaxSallaryPlayers=max_sallary.getDatabase();
        ObservableList<Player> data = FXCollections.observableList(MaxSallaryPlayers);
        MaxSalaryPlayers.setItems(data);
    }
    public void setMain(LoginApp main) {
        this.main = main;
    }

    public void BackButtonPressed(ActionEvent actionEvent) {
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
}