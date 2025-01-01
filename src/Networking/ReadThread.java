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
                        Player p= LoginApp.playerDatabase.findbyName(buyConfirmation.getPlayerName());
                        main.playerDatabase.RemovePlayer(p.getName());
                        p.setClub(buyConfirmation.getNewClubName());
                        main.playerDatabase.addPlayer(p);
                        if(buyConfirmation.getNewClubName().equals(main.username)){
                            main.buyRequests.removeIf(request -> request.getPlayerName().equals(p.getName()));
                            buyables.remove(p);
                            main.sellRequests.removeIf(request -> request.getPlayerName().equals(p.getName()));
                            sellables.add(p);
                        }
                        else if(buyConfirmation.getPreviousClubName().equals(main.username)){
                            main.sellRequests.removeIf(request -> request.getPlayerName().equals(p.getName()));
                            sellables.remove(p);
                        }
                        else{
                            main.buyRequests.removeIf(request -> request.getPlayerName().equals(p.getName()));
                            buyables.remove(p);
                        }
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
