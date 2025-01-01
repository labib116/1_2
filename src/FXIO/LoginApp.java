package FXIO;

import DTO.BuyRequest;
import DTO.SellRequest;
import Networking.ReadThread;
import Networking.SocketWrapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import Database.PlayerDatabase;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import Database.Player;

public class LoginApp extends Application {
    private Stage primaryStage;
    public static PlayerDatabase playerDatabase = new PlayerDatabase();
    private SocketWrapper socketWrapper;
    public List<SellRequest> sellRequests=new ArrayList<>();
    public List<BuyRequest>buyRequests=new ArrayList<>();
    public String username;
    public SocketWrapper getSocketWrapper() {
        return socketWrapper;
    }

    public void showLoginPage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        Parent root = loader.load();

        // Loading the controller
        LoginController controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
         primaryStage.setTitle("Login");
         primaryStage.setScene(new Scene(root));
         primaryStage.show();

    }
    public void showMainMenu(String username) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();

        // Loading the controller
        MainMenuController controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
    public void showPlayerSearch() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PlayerSearch.fxml")));
        //Stage primaryStage = new Stage();
        primaryStage.setTitle("Player Search");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
    public void showClubSearch() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ClubSearch.fxml")));
        //Stage primaryStage = new Stage();
        primaryStage.setTitle("Club Search");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
    public void showAddPlayer() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddPlayer.fxml")));
        //Stage primaryStage = new Stage();
        primaryStage.setTitle("Add Player");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
    public void showMaxSallary() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MaxSallary.fxml")));
        //Stage primaryStage = new Stage();
        primaryStage.setTitle("Max Sallary");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
    public void showTotalSallary() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TotalSallary.fxml")));
        //Stage primaryStage = new Stage();
        primaryStage.setTitle("Total Sallary");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
    public void connectToServer() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 33333;
        socketWrapper = new SocketWrapper(serverAddress, serverPort);
        new ReadThread(this);
    }
    public void showAlert() {
        // Alert the user that the username and password are incorrect
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect Credentials");
        alert.setHeaderText("Incorrect Credentials");
        alert.setContentText("The username and password you provided is not correct.");
        alert.showAndWait();
    }
    public void showTransferMarket() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("TransferMarket.fxml"));
        Parent root = loader.load();

        // Loading the controller
        TransferMarket controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        primaryStage.setTitle("Transfer Market");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            this.primaryStage=primaryStage;
            connectToServer();
            showLoginPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
        playerDatabase.load();
        //playerDatabase.printDatabase();
    }


}
