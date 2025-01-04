package FXIO;

import DTO.BuyConfirmation;
import DTO.BuyRequest;
import DTO.SellRequest;
import Database.Player;
import javafx.application.Platform;
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
import java.util.concurrent.CopyOnWriteArrayList;

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

    public static List<Player> sellables = new CopyOnWriteArrayList<>();
    public static List<Player> buyables = new CopyOnWriteArrayList<>();
    public static List<SellRequest> sellRequests = new ArrayList<>();
    public static List<BuyRequest> buyRequests = new ArrayList<>();

    private Thread autoRefreshThread;
    private volatile boolean keepRefreshing = true;
    private LoginApp main;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sellables.addAll(LoginApp.playerDatabase.findbyClub(LoginClub).getDatabase());
        initializeSellTable();
        initializeBuyTable();
        startAutoRefresh();
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
                    Player player = getTableView().getItems().get(getIndex());
                    setGraphic(player.getClub().equals(LoginClub) ? sellButton : null);
                }
            }
        });

        refreshSell();
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
            protected synchronized void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : buyButton);
            }
        });

        refreshBuy();
    }

    private synchronized void handleSell(Player player) {
        try {
            SellRequest sellRequest = new SellRequest(player.getName(), LoginClub, player.getWeekly_salary());
            if (sellRequests.contains(sellRequest)) {
                showAlert("Sell Request Error", "Player is already in the sell request list.");
                return;
            }

            sellRequests.add(sellRequest);
            main.getSocketWrapper().write(sellRequest);

            Platform.runLater(() -> {
                sellables.remove(player);
                refreshSell();
            });

            showAlert("Sell Request Sent", "Player sell request has been sent. Await confirmation.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to send sell request.");
        }
    }

    private void handleBuy(Player player) {
        try {
            //use synchronized block to avoid concurrent modification exception
            String previousClub = player.getClub();
            player.setClub(LoginClub);
            SellRequest sellRequest = new SellRequest(player.getName(),LoginClub, player.getWeekly_salary());
            sellRequests.remove(sellRequest);
            synchronized (main.getSocketWrapper()){
            BuyConfirmation buyConfirmation = new BuyConfirmation(player.getName(), previousClub, LoginClub);
            main.getSocketWrapper().write(buyConfirmation);
            Thread.sleep(100);
            }
            BuyRequest buyRequest = new BuyRequest(player.getName(), previousClub, player.getWeekly_salary());
            Platform.runLater(() -> {
                buyables.remove(player);
                sellables.add(player);
                //refreshBuy();
                //refreshSell();
                initializeSellTable();
            });

            showAlert("Buy Successful", "Player has been successfully bought.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to send buy request.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void RefreshBuyButtonPressed() {
        refreshBuy();
    }

    @FXML
    public void RefreshSellButtonPressed() {
        refreshSell();
    }

    private synchronized void refreshBuy() {
        synchronized (buyables) {
            buyables.clear();
            for (BuyRequest request : buyRequests) {
                Player player = LoginApp.playerDatabase.findbyName(request.getPlayerName());
                if (player != null) {
                    buyables.add(player);
                }
            }
        }
        Platform.runLater(() -> BuyTable.setItems(javafx.collections.FXCollections.observableArrayList(buyables)));
        //initializeBuyTable();
    }

    private synchronized void refreshSell() {
        synchronized (sellables) {
            sellables.clear();
            sellables.addAll(LoginApp.playerDatabase.findbyClub(LoginClub).getDatabase());
        }
        Platform.runLater(() -> SellTable.setItems(javafx.collections.FXCollections.observableArrayList(sellables)));
        //initializeSellTable();
    }

    private void startAutoRefresh() {
        autoRefreshThread = new Thread(() -> {
            while (keepRefreshing) {
                try {
                    synchronized (this) {
                        refreshBuy();
                        refreshSell();
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        autoRefreshThread.setDaemon(true);
        autoRefreshThread.start();
    }

    public void stopAutoRefresh() {
        keepRefreshing = false;
        if (autoRefreshThread != null && autoRefreshThread.isAlive()) {
            autoRefreshThread.interrupt();
        }
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

    public void BackButtonPressed(javafx.event.ActionEvent actionEvent) {
        try {
            stopAutoRefresh();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Parent root = loader.load();
            MainMenuController controller = loader.getController();
            controller.setMain(main);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Main Menu");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
