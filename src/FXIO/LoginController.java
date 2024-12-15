package FXIO;

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
            stage.setTitle("New Scene");*/
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setUserName(NameField.getText());
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
