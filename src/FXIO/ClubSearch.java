package FXIO;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import javafx.scene.control.Button;

public class ClubSearch {
   @FXML
    private Button MaxSallaryButton;
    @FXML
    private Button MaxHeightButton;
    @FXML
    private Button MaxAgeButton;
    @FXML
    private Button TotalSallaryButton;
    @FXML
    private Button MainMenuButton;
    public void MaxSallaryButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root=FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MaxSallary.fxml")));
        Scene scene=new Scene(root);
        Stage stage=(Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Max Sallary");
    }

    public void MaxHeightButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root=FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MaxHeight.fxml")));
        Scene scene=new Scene(root);
        Stage stage=(Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Max Height");

    }

    public void MaxAgeButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root=FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MaxAge.fxml")));
        Scene scene=new Scene(root);
        Stage stage=(Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Max Age");
    }

    public void TotalSallaryButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root=FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TotalSallary.fxml")));
        Scene scene=new Scene(root);
        Stage stage=(Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Total Sallary");
    }

    public void MainMenuButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        Scene scene = new Scene(root);
        //Stage stage = (Stage) ((Event) actionEvent).getSource();
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Main Menu");

    }
}