package Networking;

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

    public ReadThreadServer(HashMap<String, String> map, SocketWrapper socketWrapper,List clientList) {
        this.userMap = map;
        this.socketWrapper = socketWrapper;
        this.thr = new Thread(this);
        this.clientList = clientList;
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



