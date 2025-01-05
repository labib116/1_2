package FXIO;/*package FXIO;

import DTO.LoginDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static FXIO.LoginApp.playerDatabase;

public class LoginController {
    private LoginApp main;
    public static String LoginClub;;
    @FXML
    private TextField NameField;

    @FXML
    private TextField PasswordField;
    public String getUserName(){
        return NameField.getText();
    }
    public String getPassword(){
        return PasswordField.getText();
    }

    public void LoginButtonPressed(ActionEvent actionEvent) {
        try {
            /*System.out.println("Login Button Pressed");
            playerDatabase.printDatabase();

            // Retrieve username and password
            String userName = NameField.getText();
            System.out.println("Username: " + userName);

            String password = PasswordField.getText();
            System.out.println("Password: " + password);

            // Load the new scene
            Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));

            // Get the current stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(root));
            stage.setTitle("New Scene");
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setUserName(NameField.getText());
            LoginClub = NameField.getText();
            loginDTO.setPassword(PasswordField.getText());
            main.getSocketWrapper().write(loginDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void setMain(LoginApp main) {
        this.main = main;
    }
}*/

import DTO.LoginDTO;
import FXIO.LoginApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import java.io.IOException;

public class LoginController {
    //public static final ImageView GT = ;
    private LoginApp main;
    public static String LoginClub;

    @FXML
    private Label SelectedClubLabel;

    @FXML
    private PasswordField PasswordField;

    private String selectedClub = null;

    @FXML
    public void selectClub(javafx.scene.input.MouseEvent event) {
        // Get the clicked ImageView's ID
        ImageView clickedLogo = (ImageView) event.getSource();
        selectedClub = clickedLogo.getId(); // Get the ID (e.g., MI, CSK, etc.)
        SelectedClubLabel.setText("Selected Club: " + extractClubName(selectedClub));
    }
    public String extractClubName(String id) {
        if(id.contains("MI")) {
            return "Mumbai Indians";
        } else if(id.contains("CSK")) {
            return "Chennai Super Kings";
        } else if(id.contains("DC")) {
            return "Delhi Capitals";
        } else if(id.contains("PK")) {
            return "Punjab Kings";
        } else if(id.contains("KKR")) {
            return "Kolkata Knight Riders";
        } else if(id.contains("RR")) {
            return "Rajasthan Royals";
        } else if(id.contains("RCB")) {
            return "Royal Challengers Bangalore";
        } else if(id.contains("SRH")) {
            return "Sunrisers Hyderabad";
        }
        else if(id.contains("GT")) {
            return "Gujarat Titans";
        }
        else if(id.contains("LSG")) {
            return "Lucknow Super Giants";
        }
        return null;
    }
    @FXML
    public void LoginButtonPressed(ActionEvent actionEvent) {
        try {
            if (selectedClub == null) {
                SelectedClubLabel.setText("Please select a club!");
                return;
            }
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setUserName(extractClubName(selectedClub));
            LoginClub = extractClubName(selectedClub);
            loginDTO.setPassword(PasswordField.getText());
            main.getSocketWrapper().write(loginDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setMain(LoginApp main) {
        this.main = main;
    }
}



