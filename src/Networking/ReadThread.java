package Networking;

import DTO.BuyConfirmation;
import DTO.BuyRequest;
import DTO.LoginDTO;
import DTO.SellRequest;
import Database.Player;
import FXIO.LoginApp;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.IOException;

import static FXIO.TransferMarket.buyables;
import static FXIO.TransferMarket.sellables;

public class ReadThread implements Runnable {
    private final Thread thr;
    private final LoginApp main;

    public ReadThread(LoginApp main) {
        this.main = main;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = main.getSocketWrapper().read();
                if (o != null) {
                    if (o instanceof LoginDTO) {
                        LoginDTO loginDTO = (LoginDTO) o;
                        Platform.runLater(() -> {
                            if (loginDTO.isStatus()) {
                                try {
                                    main.showMainMenu(loginDTO.getUserName());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                main.showAlert();
                            }
                        });
                    } else if (o instanceof SellRequest sellRequest) {
                        BuyRequest buyRequest = new BuyRequest();
                        buyRequest.setPlayerName(sellRequest.getPlayerName());
                        buyRequest.setClubName(sellRequest.getClubName());
                        buyRequest.setPrice(sellRequest.getPrice());
                        main.buyRequests.add(buyRequest);
                        for(BuyRequest br:main.buyRequests){
                            System.out.println(br.getPlayerName());
                        }
                    } else if (o instanceof BuyConfirmation) {
                        BuyConfirmation buyConfirmation = (BuyConfirmation) o;
                        Player p=main.playerDatabase.findbyName(buyConfirmation.getPlayerName());
                        main.playerDatabase.RemovePlayer(p.getName());
                        p.setClub(buyConfirmation.getNewClubName());
                        if(buyables.contains(p)){
                            main.buyRequests.remove(p);
                            buyables.remove(p);
                        }
                        if(sellables.contains(p)){
                            sellables.remove(p);
                        }
                        main.playerDatabase.addPlayer(p);
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information");
                            alert.setHeaderText("Buy Confirmation");
                            alert.setContentText("Player " + buyConfirmation.getPlayerName() + " has been bought by " + buyConfirmation.getNewClubName());
                        });

                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error in ReadThread: " + e.getMessage());
        } finally {
            try {
                main.getSocketWrapper().closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
