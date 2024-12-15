package Networking;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {

    private ServerSocket serverSocket;
    public HashMap<String, String> userMap;

    Server() {
        userMap = new HashMap<>();
        userMap.put("Mumbai Indians", "123");
        userMap.put("Chennai Super Kings", "123");
        userMap.put("Delhi Capitals", "123");
        userMap.put("d", "d");
        userMap.put("e", "e");
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
        new ReadThreadServer(userMap, socketWrapper);
    }

    public static void main(String[] args) {
        new Server();
    }
}
