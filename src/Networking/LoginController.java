package Networking;

import DTO.LoginDTO;
import FXIO.LoginApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;


public class LoginController {

    private LoginApp main;

    @FXML
    private TextField userText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button resetButton;

    @FXML
    private Button loginButton;
    public String getUserName(){
        return userText.getText();
    }
    public String getPassword(){
        return passwordText.getText();
    }

    @FXML
    void loginAction(ActionEvent event) {

        String userName = userText.getText();
        main.username=userName;
        String password = passwordText.getText();
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserName(userName);
        loginDTO.setPassword(password);
        try {
            main.getSocketWrapper().write(loginDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void resetAction(ActionEvent event) {
        userText.setText(null);
        passwordText.setText(null);
    }

    void setMain(LoginApp main) {
        this.main = main;
    }

}
