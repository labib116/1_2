package Networking;

import DTO.BuyConfirmation;
import DTO.LoginDTO;
import DTO.SellRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadThreadServer implements Runnable {
    private final Thread thr;
    private final SocketWrapper socketWrapper;
    public HashMap<String, String> userMap;
    public HashMap<String,String>PlayerMap=new HashMap<>();
    public List<SocketWrapper> clientList=new ArrayList<>();
    public HashMap<SocketWrapper,String>clientMap=new HashMap<>();

    public ReadThreadServer(HashMap<String, String> map, SocketWrapper socketWrapper,List clientList,HashMap<SocketWrapper,String>clientMap) {
        this.userMap = map;
        this.socketWrapper = socketWrapper;
        this.thr = new Thread(this);
        this.clientList = clientList;
        this.clientMap = clientMap;
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = socketWrapper.read();
                if (o != null) {
                    if (o instanceof LoginDTO) {
                        LoginDTO loginDTO = (LoginDTO) o;
                        String password = userMap.get(loginDTO.getUserName());
                        loginDTO.setStatus(loginDTO.getPassword().equals(password));
                        System.out.println("Adding to clientMap: " + socketWrapper);
                        synchronized (clientMap){
                        clientMap.put(socketWrapper,loginDTO.getUserName());
                        }
                        System.out.println("clientMap contents: " + clientMap);
                        System.out.println("Login Successful for: " + loginDTO.getUserName());
                        System.out.println("Login Successful");
                        socketWrapper.write(loginDTO);
                    }
                    if(o instanceof SellRequest){
                        System.out.println("Sell Request Received");
                        SellRequest sellRequest = (SellRequest) o;
                        PlayerMap.put(sellRequest.getPlayerName(),sellRequest.getClubName());
                        for(SocketWrapper client:clientList){
                            if(client!=socketWrapper){System.out.println("Sell request forwarded");
                            client.write(sellRequest);
                            }
                        }

                    }
                    if(o instanceof BuyConfirmation){
                        System.out.println("Buy Confirmation Received");
                        BuyConfirmation buyConfirmation = (BuyConfirmation) o;
                        for(SocketWrapper client:clientList){
                            System.out.println("Buy Confirmation forwarded");
                            client.write(buyConfirmation);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                socketWrapper.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



