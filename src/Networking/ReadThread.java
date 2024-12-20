package Networking;

import DTO.BuyRequest;
import DTO.LoginDTO;
import DTO.SellRequest;
import FXIO.LoginApp;
import javafx.application.Platform;

import java.io.IOException;

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
