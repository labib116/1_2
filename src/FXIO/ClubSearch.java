package FXIO;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
    private LoginApp main;
    public void MaxSallaryButtonPressed(ActionEvent actionEvent) throws IOException {
        try {
            //cleanup();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("MaxSallary.fxml"));
            Parent root = loader.load();

            // Loading the controller
            MaxSallary controller = loader.getController();
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

    public void MaxHeightButtonPressed(ActionEvent actionEvent) throws IOException {
        try {
            //cleanup();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("MaxHeight.fxml"));
            Parent root = loader.load();

            // Loading the controller
            MaxHeight controller = loader.getController();
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

    public void MaxAgeButtonPressed(ActionEvent actionEvent) throws IOException {
        try {
            //cleanup();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("MaxAge.fxml"));
            Parent root = loader.load();

            // Loading the controller
            MaxAge controller = loader.getController();
            controller.setMain(main);

            // Set the primary stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Max Age");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void TotalSallaryButtonPressed(ActionEvent actionEvent) throws IOException {
        try {
            //cleanup();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("TotalSallary.fxml"));
            Parent root = loader.load();

            // Loading the controller
            TotalSallary controller = loader.getController();
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

    public void MainMenuButtonPressed(ActionEvent actionEvent) throws IOException {
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
        this.main=main;
    }
}