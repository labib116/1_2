package Networking;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {

    private ServerSocket serverSocket;
    public HashMap<String, String> userMap=new HashMap<>();
    List<SocketWrapper>clientList=new ArrayList<>();

    Server() {
        userMap = new HashMap<>();
        userMap.put("Mumbai Indians", "123");
        userMap.put("Chennai Super Kings", "123");
        userMap.put("Delhi Capitals", "123");
        userMap.put("Kolkata Knight Riders", "123");
        userMap.put("Royal Challengers Bangalore", "123");
        userMap.put("Sunrisers Hyderabad", "123");
        userMap.put("Rajasthan Royals", "123");
        userMap.put("Punjab Kings", "123");
        userMap.put("Lucknow Super Giants", "123");
        userMap.put("Gujarat Titans", "123");
        try {
            serverSocket = new ServerSocket(33333);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }

    public void serve(Socket clientSocket) throws IOException {
        SocketWrapper socketWrapper = new SocketWrapper(clientSocket);
        clientList.add(socketWrapper);
        new ReadThreadServer(userMap, socketWrapper,clientList);
    }

    public static void main(String[] args) {
        new Server();
    }
}
