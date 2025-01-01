package FXIO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static FXIO.LoginApp.playerDatabase;

public class TotalSallary {
    @FXML
    public TextField ClubNameField;
    @FXML
    public TextArea OutputTextArea;
    @FXML
    public Button SearchButton;
    private LoginApp main;

    public void SearchButtonPressed(ActionEvent actionEvent) {
        String ClubName = ClubNameField.getText();
        long total_sallary=playerDatabase.find_total_sallary(ClubName);
        OutputTextArea.setText("Total Salary of "+ClubName+" is "+total_sallary+"$");

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
            PlayerSearchController controller = loader.getController();
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