package Networking;

import DTO.BuyConfirmation;
import DTO.BuyRequest;
import DTO.LoginDTO;
import DTO.SellRequest;
import Database.Player;
import FXIO.LoginApp;
import javafx.application.Platform;

import java.io.IOException;
import java.util.List;

import static FXIO.TransferMarket.buyables;
import static FXIO.TransferMarket.buyRequests;
import static FXIO.TransferMarket.sellRequests;

public class ReadThread implements Runnable {
    private final Thread thread;
    private final LoginApp main;

    public ReadThread(LoginApp main) {
        this.main = main;
        this.thread = new Thread(this);
        thread.start();
    }

    /*@Override
    public void run() {
        try {
            while (true) {
                Object message = main.getSocketWrapper().read();
                if (message != null) {
                    if (message instanceof LoginDTO loginDTO) {
                        handleLoginDTO(loginDTO);
                    } else if (message instanceof SellRequest sellRequest) {
                        handleSellRequest(sellRequest);
                    } else if (message instanceof BuyConfirmation buyConfirmation) {
                        handleBuyConfirmation(buyConfirmation);
                    }
                    else if (message instanceof Player player){
                        handlePlayer(player);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in ReadThread: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                main.getSocketWrapper().closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/
    @Override
    public void run() {
        try {
            while (true) {
                Object message = main.getSocketWrapper().read();
                if (message != null) {
                    if (message instanceof LoginDTO loginDTO) {
                        handleLoginDTO(loginDTO);
                    } else if (message instanceof SellRequest sellRequest) {
                        handleSellRequest(sellRequest);
                    } else if (message instanceof BuyConfirmation buyConfirmation) {
                        handleBuyConfirmation(buyConfirmation);
                    } else if (message instanceof Player player) {
                        handlePlayer(player);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error in ReadThread: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                main.getSocketWrapper().closeConnection();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    private void handlePlayer(Player player){
        if(LoginApp.playerDatabase.findbyName(player.getName())==null){
            LoginApp.playerDatabase.addPlayer(player);
            System.out.println("Player added to database: "+player.getName());
        }
    }

    private void handleLoginDTO(LoginDTO loginDTO) {
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
    }

    private void handleSellRequest(SellRequest sellRequest) {
        synchronized (buyRequests) {
            BuyRequest buyRequest = new BuyRequest(sellRequest.getPlayerName(), sellRequest.getClubName(), sellRequest.getPrice());

            if (!buyRequests.contains(buyRequest)) {
                buyRequests.add(buyRequest);

                Player player = LoginApp.playerDatabase.findbyName(sellRequest.getPlayerName());
                if (player != null && !buyables.contains(player)) {
                    buyables.add(player);
                   // Platform.runLater(() -> FXIO.TransferMarket.refreshBuy());
                } else {
                    System.err.println("Player not found or already in buyables for SellRequest: " + sellRequest.getPlayerName());
                }
            }
        }
    }

    private void handleBuyConfirmation(BuyConfirmation buyConfirmation) {
        Player player = LoginApp.playerDatabase.findbyName(buyConfirmation.getPlayerName());
        if (player == null) {
            System.err.println("Player not found in database: " + buyConfirmation.getPlayerName());
            return;
        }

        synchronized (sellRequests) {
            synchronized (buyRequests) {
                if (buyConfirmation.getNewClubName().equals(main.username)) {
                    // Player bought by this club
                    buyRequests.removeIf(request -> request.getPlayerName().equalsIgnoreCase(player.getName()));
                    buyables.remove(player);
                } else if (buyConfirmation.getPreviousClubName().equals(main.username)) {
                    // Player sold by this club
                    sellRequests.removeIf(request -> request.getPlayerName().equalsIgnoreCase(player.getName()));
                } else {
                    // Transaction between other clubs
                    buyRequests.removeIf(request -> request.getPlayerName().equalsIgnoreCase(player.getName()));
                    buyables.remove(player);
                }
            }
        }

        // Update player's club in the database
        player.setClub(buyConfirmation.getNewClubName());
        LoginApp.playerDatabase.RemovePlayer(player.getName());
        LoginApp.playerDatabase.addPlayer(player);
        List<Player>new_players=LoginApp.playerDatabase.getDatabase();
        LoginApp.playerDatabase.add_to_file(new_players);

        /*Platform.runLater(() -> {
            FXIO.TransferMarket.refreshBuy();
            FXIO.TransferMarket.refreshSell();
        });*/

        System.out.println("BuyConfirmation processed for player: " + player.getName());
    }

    public boolean isAlive() {
        return thread.isAlive();
    }
}
