package Server;

import Server.Resource.Token;
import Server.Resource.TokenManager;
import Server.Utils.Connection;
import Server.Utils.Queue;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import static Server.Utils.Constants.INIT_TOKEN;
import static Server.Utils.Constants.PORT;

public class Listener {
    private static int numberConnections = 0;
    public static Queue queue;
    public static Connection current;

    public static void main(String args[]) {
        Socket socket = null;
        ServerSocket serverSocket = null;
        TokenManager tokenManager;
        tokenManager = new TokenManager(INIT_TOKEN);
        queue = new Queue();
        current = null;
        System.out.println("Server Listening......");

        try {
            serverSocket = new ServerSocket(PORT); // can also use static final PORT_NUM , when defined
            serverSocket.setSoTimeout(500);
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Server error");
        }

        while(true) {
            try{
                assert serverSocket != null;
                connectToThread(serverSocket, socket, tokenManager);
            } catch (SocketTimeoutException e) {
                System.out.print("\b-");
            } catch(Exception e){
                e.printStackTrace();
                System.out.println("Connection Error");
            }
            current = update(current);
        }
    }

    private static void connectToThread(ServerSocket serverSocket, Socket socket, TokenManager tokenManager) throws IOException {
        socket = serverSocket.accept();
        Connection connection = new Connection(numberConnections++);
        queue.pushQueue(connection);
        System.out.println("Connection Established [ID: " + connection.getId() + "]");
        ServerThread serverThread = new ServerThread(socket, tokenManager, connection);
        serverThread.start();
    }

    private static Connection update(Connection conn) {
        if(queue.isEmpty())
            return null;

        if(conn == null || conn.isClosed())
            conn = queue.popQueue().free();

        return conn;
    }
}