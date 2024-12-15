package FXIO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import static FXIO.LoginApp.playerDatabase;

public class TotalSallary {
    @FXML
    public TextField ClubNameField;
    @FXML
    public TextArea OutputTextArea;
    @FXML
    public Button SearchButton;
    public void SearchButtonPressed(ActionEvent actionEvent) {
        String ClubName = ClubNameField.getText();
        long total_sallary=playerDatabase.find_total_sallary(ClubName);
        OutputTextArea.setText("Total Salary of "+ClubName+" is "+total_sallary+"$");

    }
}