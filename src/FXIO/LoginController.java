package FXIO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField NameField;

    @FXML
    private TextField PasswordField;

    public void LoginButtonPressed(ActionEvent actionEvent) {
        try {
            System.out.println("Login Button Pressed");

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
