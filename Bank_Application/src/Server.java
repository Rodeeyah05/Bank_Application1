import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket serverSocket;
    int port;
    boolean running = false;

    public Server( int port) {
        this.port = port;
    }
    public void start() {
        try{
        running = true;
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server started. Listening on port " + port);
        while (running) {
           Socket socket = serverSocket.accept();
           System.out.println("Accepted connection from " + socket.getInetAddress());
           ClientHandler clientHandler = new ClientHandler(socket);
           Thread t = new Thread(clientHandler);
           t.start();
        }

    } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void stop() {
        try{
            running = false;
            if(serverSocket != null){
                serverSocket.close();
                System.out.println("Server stopped");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void main() {
        Server server = new Server(8080);
        server.start();
    }
}
