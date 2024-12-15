package Networking;

import DTO.LoginDTO;

import java.io.IOException;
import java.util.HashMap;

public class ReadThreadServer implements Runnable {
    private final Thread thr;
    private final SocketWrapper socketWrapper;
    public HashMap<String, String> userMap;


    public ReadThreadServer(HashMap<String, String> map, SocketWrapper socketWrapper) {
        this.userMap = map;
        this.socketWrapper = socketWrapper;
        this.thr = new Thread(this);
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



