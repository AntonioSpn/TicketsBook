import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private ServerSocket socket;
    private Socket client_socket;
    private int port;
    int client_id = 0;
    private UserHash hash;

    public static void main(String args[]){        
        try {
            Server server = new Server(7001);
            server.start();
        } catch (IOException e) { e.printStackTrace(); }
        catch (ClassNotFoundException e) { e.printStackTrace(); }        
    }

    public Server(int port) throws IOException, ClassNotFoundException {
        System.out.println("Initializing server with port "+port);
        this.port = port;
        this.hash= new UserHash();
    }

    public void start() {
        try {
            System.out.println("Starting server on port "+port);
            socket = new ServerSocket(port);
            System.out.println("Started server on port "+port);
            while (true) {
                System.out.println("Listening on port " + port);
                client_socket = socket.accept();
                System.out.println("Accepted connection from " + client_socket.getRemoteSocketAddress());
                ClientManager cm = new ClientManager(client_socket,hash);
                Thread t = new Thread(cm,"client_"+client_id); // primo parametro funzione che esegue, secondo nome del thread
                client_id++;
                t.start();
            }
        } catch (IOException e) {
            System.out.println("Could not start server on port "+port);
            e.printStackTrace();
        }
    }
}