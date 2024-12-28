package FXIO;

import DTO.BuyConfirmation;
import DTO.BuyRequest;
import DTO.SellRequest;
import Database.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static FXIO.LoginController.LoginClub;

public class TransferMarket implements Initializable {

    @FXML
    private TableView<Player> BuyTable;
    @FXML
    private TableColumn<Player, String> BuyPlayerNameColumn;
    @FXML
    private TableColumn<Player, String> BuyClubColumn;
    @FXML
    private TableColumn<Player, Integer> BuySalaryColumn;
    @FXML
    private TableColumn<Player, Void> BuyActionColumn;

    @FXML
    private TableView<Player> SellTable;
    @FXML
    private TableColumn<Player, String> SellPlayerNameColumn;
    @FXML
    private TableColumn<Player, String> SellClubColumn;
    @FXML
    private TableColumn<Player, Integer> SellSalaryColumn;
    @FXML
    private TableColumn<Player, Void> SellActionColumn;

    @FXML
    private Button RefreshBuyButton;
    @FXML
    private Button RefreshSellButton;

    private static int sellIndex = 0;
    private static int buyIndex = 0;

    public static List<Player> sellables = LoginApp.playerDatabase.findbyClub(LoginClub).getDatabase();
    public static List<Player> buyables = new ArrayList<>();

    private LoginApp main;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sellables = LoginApp.playerDatabase.findbyClub(LoginClub).getDatabase();
        initializeSellTable();
        initializeBuyTable();
        //refresh();
    }

    private void initializeSellTable() {
        SellPlayerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        SellClubColumn.setCellValueFactory(new PropertyValueFactory<>("club"));
        SellSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("weekly_salary"));

        SellActionColumn.setCellFactory(column -> new TableCell<>() {
            private final Button sellButton = new Button("Sell");

            {
                sellButton.setOnAction(event -> {
                    Player player = getTableView().getItems().get(getIndex());
                    handleSell(player);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(sellButton);
                }
            }
        });

        SellTable.setItems(javafx.collections.FXCollections.observableArrayList(sellables));
    }

    private void initializeBuyTable() {
        BuyPlayerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        BuyClubColumn.setCellValueFactory(new PropertyValueFactory<>("club"));
        BuySalaryColumn.setCellValueFactory(new PropertyValueFactory<>("weekly_salary"));

        BuyActionColumn.setCellFactory(column -> new TableCell<>() {
            private final Button buyButton = new Button("Buy");

            {
                buyButton.setOnAction(event -> {
                    Player player = getTableView().getItems().get(getIndex());
                    handleBuy(player);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buyButton);
                }
            }
        });

        BuyTable.setItems(javafx.collections.FXCollections.observableArrayList(buyables));
    }

    private void handleSell(Player player) {
        try {
            SellRequest sellRequest = new SellRequest();
            sellRequest.setPlayerName(player.getName());
            sellRequest.setClubName(LoginClub);
            sellRequest.setPrice(player.getWeekly_salary());

            if (main.sellRequests.contains(sellRequest)) {
                showAlert("Player Already in Sell Request", "The player is already in the sell request list.");
            } else {
                main.sellRequests.add(sellRequest);
                main.getSocketWrapper().write(sellRequest);
                sellables.remove(player);
                refresh();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleBuy(Player player) {
        try {
            String previousClub = player.getClub();
            player.setClub(LoginClub);

            BuyConfirmation buyConfirmation = new BuyConfirmation();
            buyConfirmation.setPlayerName(player.getName());
            buyConfirmation.setPreviousClubName(previousClub);
            buyConfirmation.setNewClubName(LoginClub);

            LoginApp.playerDatabase.RemovePlayer(player.getName());
            LoginApp.playerDatabase.addPlayer(player);

            main.buyRequests.removeIf(request -> request.getPlayerName().equals(player.getName()));
            main.getSocketWrapper().write(buyConfirmation);
            buyables.remove(player);

            refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void RefreshBuyButtonPressed(ActionEvent actionEvent) {
        refresh();
    }

    @FXML
    public void RefreshSellButtonPressed(ActionEvent actionEvent) {
        refresh();
    }

    private void refresh() {
        sellables = LoginApp.playerDatabase.findbyClub(LoginClub).getDatabase();
        buyables.clear();

        for (BuyRequest request : main.buyRequests) {
            Player foundPlayer = LoginApp.playerDatabase.findbyName(request.getPlayerName());
            if (foundPlayer != null) {
                buyables.add(foundPlayer);
            }
        }

        SellTable.setItems(javafx.collections.FXCollections.observableArrayList(sellables));
        BuyTable.setItems(javafx.collections.FXCollections.observableArrayList(buyables));
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setMain(LoginApp main) {
        this.main = main;
    }

    public void BackButtonPressed(ActionEvent actionEvent) {
        try {
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
}