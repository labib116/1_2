package FXIO;

import Database.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public  class NameSearch {
    @FXML
    public TextField NameInputField;
    @FXML
    public TextField NameField;
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
    public TextField JerseyNumberField;
    @FXML
    public TextField WeeklySalaryField;
    @FXML
    public Button BackButton;
    @FXML
    public Button SearchButton;
    private LoginApp main;

    public void BackButtonPressed(ActionEvent actionEvent) throws IOException {
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
            stage.setTitle("Main Menu");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SearchButtonPressed(ActionEvent actionEvent) {
        String name = NameInputField.getText();
        Player player = LoginApp.playerDatabase.findbyName(name);
        if (player == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Player Not Found");
            alert.setHeaderText(null); // No header text
            alert.setContentText("Player is not Currently in the Database. Please try again with a different name.");
            alert.showAndWait();
        } else {
            NameField.setText(player.getName());
            CountryField.setText(player.getCountry());
            AgeField.setText(String.valueOf(player.getAge()));
            HeightField.setText(String.valueOf(player.getHeight()));
            ClubField.setText(player.getClub());
            PositionField.setText(player.getPosition());
            if(player.getNumber()==-1)
                JerseyNumberField.setText("N/A");
            else{
                JerseyNumberField.setText(String.valueOf(player.getNumber()));
            }
            WeeklySalaryField.setText(String.valueOf(player.getWeekly_salary()));
        }
    }
    public void setMain(LoginApp main) {
        this.main = main;
    }
}