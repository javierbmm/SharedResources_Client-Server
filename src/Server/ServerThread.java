package Server;// echo server
import Server.Resource.TokenManager;
import Server.Utils.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static Server.Utils.Constants.*;

class ServerThread extends Thread {
    private static int waitingIt = 0;
    private TokenManager tokenManager;
    private Connection connection;
    String line = null;
    BufferedReader input = null;
    PrintWriter output = null;
    Socket socket = null;

    public ServerThread(Socket s, TokenManager tokenManager, Connection connection){
        this.socket = s;
        this.tokenManager = tokenManager;
        this.connection = connection;
    }

    public void run() {
        initIO();

        // Wait for connection to be free'd
        while(this.connection.isLocked()) {
            output.println(waiting());
            output.flush();
            // System.out.println(waiting());
        }
        System.out.println("Connection ready " + connection.getId());

        try {
            output.println("⏱");
            output.flush();
            Thread.sleep(450);
            output.println("Enter command to server (READ, UPDATE, QUIT):");
            output.flush();
            talkToClient();
        } catch (IOException e) {
            line = this.getName(); //reused String line for getting thread name
            System.out.println("IO Error/ Client " + line + " terminated abruptly");
        } catch(NullPointerException e) {
            line=this.getName(); //reused String line for getting thread name
            System.out.println("Client " + line + " Closed");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private void initIO() {
        try{
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream());
        } catch(IOException e) {
            System.out.println("IO error in server thread");
        }
    }

    private void talkToClient() throws IOException, NullPointerException {
        line = input.readLine();
        do{
            output.println("Token response: " + tokenManager.handle(line));
            output.flush();
            // System.out.println("Token response: " + tokenManager.handle(line));
            line=input.readLine();
        }while(line.compareTo(CLOSE)!=0);
    }

    private void closeConnection() {
        try{
            System.out.println("Connection Closing..");
            closeInput();
            closeOutput();
            closeSocket();
            this.connection.close();
        } catch(IOException ie){
            System.out.println("Socket Close Error");
        }
    }

    private void closeOutput() throws IOException {
        if(output != null){
            output.close();
            System.out.println("Socket Out closed");
        }
    }

    private void closeInput() throws IOException {
        if (input != null){
            input.close();
            System.out.println(" Socket Input Stream closed");
        }
    }

    private void closeSocket() throws IOException {
        if (socket != null){
            socket.close();
            System.out.println("Socket closed");
        }
    }

    private String waiting() {
        String loading = "⣾⣽⣻⢿⡿⣟⣯⣷";
        waitingIt = (++waitingIt >= loading.length())? 0 : waitingIt;

        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return String.format("\b\b%c", loading.charAt(waitingIt));
    }
}
