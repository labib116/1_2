package Networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

public class SocketWrapper {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    // Constructor for clients
    public SocketWrapper(String s, int port) throws IOException {
        this.socket = new Socket(s, port);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    // Constructor for server-side
    public SocketWrapper(Socket s) throws IOException {
        this.socket = s;
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public Object read() throws IOException, ClassNotFoundException {
        return ois.readObject();
    }

    public void write(Object o) throws IOException {
        oos.writeObject(o);
    }

    public void closeConnection() throws IOException {
        ois.close();
        oos.close();
        socket.close(); // Ensure the socket is closed too
    }

    // Override equals and hashCode to ensure correct behavior when used in HashMap
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Reference equality check
        if (o == null || getClass() != o.getClass()) return false; // Type check

        SocketWrapper that = (SocketWrapper) o;

        // Check equality based on the socket (unique identifier for connections)
        return Objects.equals(this.socket, that.socket);
    }

    @Override
    public int hashCode() {
        // Generate hash code based on the socket
        return Objects.hash(socket);
    }

    // Optionally, add a toString method for better logging and debugging
    @Override
    public String toString() {
        return "SocketWrapper{" +
                "socket=" + socket +
                '}';
    }
}
