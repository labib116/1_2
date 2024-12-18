package Networking;

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
                        System.out.println(loginDTO.getUserName());
                        System.out.println(loginDTO.isStatus());
                        // the following is necessary to update JavaFX UI components from user created threads
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (loginDTO.isStatus()) {
                                    try {
                                        main.showMainMenu(loginDTO.getUserName());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (o instanceof SellRequest) {
                                    // Handle SellRequest and update UI
                                    SellRequest sellRequest = (SellRequest) o;
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Handle SellRequest (e.g., update labels, show alert)
                                            System.out.println("Received SellRequest for " + sellRequest.getPlayerName());
                                            // For example, update a UI label:
                                           // main.updateSellStatus(sellRequest.getPlayerName(), sellRequest.getClubName());
                                        }
                                    });
                                }
                                else {
                                    main.showAlert();
                                }

                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                main.getSocketWrapper().closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



