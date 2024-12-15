package FXIO;

import Database.Player;
import Database.PlayerDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class MaxHeight {
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
    public TableView MaxHeightPlayers;

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
        PlayerDatabase max_height=LoginApp.playerDatabase.findMaxHeight(ClubName);
        List<Player>MaxSallaryPlayers=max_height.getDatabase();
        ObservableList<Player> data = FXCollections.observableList(MaxSallaryPlayers);
        MaxHeightPlayers.setItems(data);
    }
}