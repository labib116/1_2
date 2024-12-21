package FXIO;

import DTO.BuyConfirmation;
import DTO.BuyRequest;
import DTO.SellRequest;
import Database.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static FXIO.LoginController.LoginClub;

public class TransferMarket implements Initializable {
    public TextField BuyName;
    public TextField BuyClub;
    public TextField BuySalary;
    public Button BuyButt;
    public Button BuyNext;
    public Button BacButton;
    public TextField SellName;
    public TextField SellClub;
    public TextField SellSalary;
    public Button SellPrevious;
    public Button SellButton;
    public Button SellNext;
    private static int index=0;
    private int buyIndex=0;
    public static List<Player> sellables=LoginApp.playerDatabase.findbyClub(LoginClub).getDatabase();
    public static List<Player> buyables=new ArrayList<>();
    public Button BuyPrevious;
    private LoginApp main;

    public void initialize(URL location, ResourceBundle resources) {
        // Ensure that sellables is properly initialized
        sellables = LoginApp.playerDatabase.findbyClub(LoginClub).getDatabase();
        //buyables = LoginApp.playerDatabase.findbyClub(LoginClub).getDatabase();

        if (!sellables.isEmpty()) {
            updateSellablePlayer();
            //updateBuyablePlayer();
        }
    }

    private void updateSellablePlayer() {
        if(sellables.isEmpty()){
            SellName.clear();
            SellClub.clear();
            SellSalary.clear();
            return;
        }
        else{
            SellName.setText(sellables.get(index).getName());
            SellClub.setText(sellables.get(index).getClub());
            SellSalary.setText(String.valueOf(sellables.get(index).getWeekly_salary()));
        }

    }
    private void updateBuyablePlayer() {
        if(!buyables.isEmpty()) {
            BuyRequest buyRequest = main.buyRequests.get(buyIndex);
            if (buyRequest != null) {
                BuyName.setText(buyables.get(index).getName());
                BuyClub.setText(buyables.get(index).getClub());
                BuySalary.setText(String.valueOf(buyables.get(index).getWeekly_salary()));
            } else {
                //clearBuyablePlayer();
                System.out.println("No player found at index: " + index);
            }
        }
    }

    public void BuyNextPressed(ActionEvent actionEvent) {
        if(buyIndex<buyables.size()-1){
            buyIndex++;
            BuyName.setText(buyables.get(buyIndex).getName());
            BuyClub.setText(buyables.get(buyIndex).getClub());
            BuySalary.setText(String.valueOf(buyables.get(buyIndex).getWeekly_salary()));
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("No Next Player");
            alert.setContentText("You are already viewing the last player. There is no Next player.");
            alert.showAndWait();
        }

    }

    public void BuyPreviousPressed(ActionEvent actionEvent) {
        if(buyIndex>0){
            buyIndex--;
            BuyName.setText(buyables.get(buyIndex).getName());
            BuyClub.setText(buyables.get(buyIndex).getClub());
            BuySalary.setText(String.valueOf(buyables.get(buyIndex).getWeekly_salary()));
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("No Previous Player");
            alert.setContentText("You are already viewing the first player. There is no previous player.");
            alert.showAndWait();
        }

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

    public void SellPreviousBUttonPressed(ActionEvent actionEvent) {
        if(index>0){
            index--;
            SellName.setText(sellables.get(index).getName());
            SellClub.setText(sellables.get(index).getClub());
            SellSalary.setText(String.valueOf(sellables.get(index).getWeekly_salary()));
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("No Previous Player");
            alert.setContentText("You are already viewing the first player. There is no previous player.");
            alert.showAndWait();
        }
    }

    public void SellButtonPressed(ActionEvent actionEvent) {
        try {
            if (!sellables.isEmpty()) {
                if (index < sellables.size() - 1 && sellables.size() > 1) {
                    SellName.clear();
                    SellClub.clear();
                    SellSalary.clear();
                    SellName.setText(sellables.get(index + 1).getName());
                    SellClub.setText(sellables.get(index + 1).getClub());
                    SellSalary.setText(String.valueOf(sellables.get(index + 1).getWeekly_salary()));
                    SellRequest sellRequest = new SellRequest();
                    sellRequest.setPlayerName(sellables.get(index).getName());
                    sellRequest.setClubName(LoginClub);
                    sellRequest.setPrice(sellables.get(index).getWeekly_salary());
                    if (main.sellRequests.contains(sellRequest)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText("Player Already in Sell Request");
                        alert.setContentText("The player you are trying to sell is already in the sell request list.");
                        alert.showAndWait();
                        return;
                    } else {
                        main.sellRequests.add(sellRequest);
                        main.getSocketWrapper().write(sellRequest);
                    }
                }
                if(sellables.size()==1){
                    SellName.clear();
                    SellClub.clear();
                    SellSalary.clear();
                    SellRequest sellRequest = new SellRequest();
                    sellRequest.setPlayerName(sellables.get(index).getName());
                    sellRequest.setClubName(LoginClub);
                    sellRequest.setPrice(sellables.get(index).getWeekly_salary());
                    main.sellRequests.add(sellRequest);
                    main.getSocketWrapper().write(sellRequest);
                }


            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void SellNextPressed(ActionEvent actionEvent) {
        if(index<sellables.size()-1){
            index++;
            SellName.setText(sellables.get(index).getName());
            SellClub.setText(sellables.get(index).getClub());
            SellSalary.setText(String.valueOf(sellables.get(index).getWeekly_salary()));
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("No Next Player");
            alert.setContentText("You are already viewing the last player. There is no Next player.");
            alert.showAndWait();
        }
    }
    public void setMain(LoginApp main) {
        this.main = main;
    }
    public void refresh() {
        sellables = LoginApp.playerDatabase.findbyClub(LoginClub).getDatabase();
        buyables.clear(); // Clear previous entries
        buyIndex = 0; // Reset index
        index = 0; // Reset sellables index

        for (BuyRequest player : main.buyRequests) {
            Player foundPlayer = LoginApp.playerDatabase.findbyName(player.getPlayerName());
            if (foundPlayer != null) {
                buyables.add(foundPlayer);
            }
        }
        updateBuyablePlayer();
        updateSellablePlayer();
    }



    public void RefreshButtonPressed(ActionEvent actionEvent) {
        refresh();
    }

    public void BuyButtonPressed(ActionEvent actionEvent) {
        try{
            if(!buyables.isEmpty()){
                if(buyIndex<buyables.size()-1 && buyables.size()>1){
                    BuyName.clear();
                    BuyClub.clear();
                    BuySalary.clear();
                    if(buyIndex+1<buyables.size()) {
                        BuyName.setText(buyables.get(buyIndex + 1).getName());
                        BuyClub.setText(buyables.get(buyIndex + 1).getClub());
                        BuySalary.setText(String.valueOf(buyables.get(buyIndex + 1).getWeekly_salary()));
                    }
                    Player player = buyables.get(buyIndex);
                    LoginApp.playerDatabase.RemovePlayer(player.getName());
                    String previousClub = player.getClub();
                    player.setClub(LoginClub);
                    LoginApp.playerDatabase.addPlayer(player);
                    BuyConfirmation buyConfirmation = new BuyConfirmation();
                    buyConfirmation.setPlayerName(player.getName());
                    buyConfirmation.setPreviousClubName(previousClub);
                    buyConfirmation.setNewClubName(LoginClub);
                    main.buyRequests.remove(player);
                    main.getSocketWrapper().write(buyConfirmation);
                }
                if(buyables.size()==1){
                    BuyName.clear();
                    BuyClub.clear();
                    BuySalary.clear();
                    Player player = buyables.get(buyIndex);
                    LoginApp.playerDatabase.RemovePlayer(player.getName());
                    String previousClub = player.getClub();
                    player.setClub(LoginClub);
                    LoginApp.playerDatabase.addPlayer(player);
                    BuyConfirmation buyConfirmation = new BuyConfirmation();
                    buyConfirmation.setPlayerName(player.getName());
                    buyConfirmation.setPreviousClubName(previousClub);
                    buyConfirmation.setNewClubName(LoginClub);
                    main.buyRequests.remove(player);
                    main.getSocketWrapper().write(buyConfirmation);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
