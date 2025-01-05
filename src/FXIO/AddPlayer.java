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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import Database.PlayerDatabase;
import javafx.stage.Stage;

import javax.lang.model.type.NullType;

import java.io.IOException;
import java.util.List;
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
    @FXML
    public ChoiceBox<String> PositionChoicebox;
    @FXML
    public ChoiceBox<String> ClubChoicebox;
    private LoginApp main;
    @FXML
    public void initialize(){
        PositionChoicebox.getItems().addAll("Batsman", "Bowler", "Wicketkeeper", "Allrounder");
        PositionChoicebox.setValue("Batsman");
        ClubChoicebox.getItems().addAll(
                "Mumbai Indians",
                "Chennai Super Kings",
                "Delhi Capitals",
                "Kolkata Knight Riders",
                "Royal Challengers Bangalore",
                "Sunrisers Hyderabad",
                "Rajasthan Royals",
                "Punjab Kings",
                "Lucknow Super Giants",
                "Gujarat Titans"
        );
        ClubChoicebox.setValue("Mumbai Indians");

    }



    public void AddButtonPressed(ActionEvent actionEvent) throws IOException {
        String playerName = PlayerNameField.getText();
        String country = CountryField.getText();
        int age = Integer.parseInt(AgeField.getText());
        double height= Double.parseDouble(HeightField.getText());
        String club = ClubChoicebox.getValue();
        String position = PositionChoicebox.getValue();
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
            main.getSocketWrapper().write(p);
            List<Player> players=playerDatabase.getDatabase();
            playerDatabase.add_to_file(players);
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
    public void setMain(LoginApp main) {
        this.main = main;
    }
}