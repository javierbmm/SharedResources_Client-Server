package Client;

import static Server.Utils.Constants.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class NetworkDriver {
    private static InetAddress address;
    private static Socket socket;
    private static BufferedReader bufferReader;
    private static BufferedReader inputSocket;
    private static PrintWriter outputSocket;

    private static int waitingIt = 0;
    public static void main(String args[]) throws Exception {
        Type type;
        if(args[0].length() == 0 || args[0].equals("readonly"))
            type = Type.READONLY;
        else if(args[0].equals("updater"))
            type = Type.UPDATER;
        else {
            throw new Exception("Wrong argument. Please select type one ('readonly', 'updater' or leave it empty)");
        }

        address = InetAddress.getLocalHost();
        socket = null;
        // From keyboard:
        bufferReader = null;
        // From socket (server):
        inputSocket = null;
        // To socket (server):
        outputSocket = null;
        String line = null;
        Operator operator = new Operator();

        initIO();
        operator.setChannelOut(outputSocket)
                .setChannelIn(inputSocket)
                .setType(type);

        System.out.println("Client Address : " + address);

        String response=null;
        try {
            waitForServer();

            String greetings;
            greetings = inputSocket.readLine();
            System.out.println("\n" + greetings);
            operator.talk();
//            do {
//                // TODO: Automatize the communication process with a for loop and a couple of commands as in the
//                //  exercise statement
//                assert outputSocket != null;
//                outputSocket.println(line);
//                outputSocket.flush();
//                response = inputSocket.readLine();
//                System.out.println("Server Response : " + response);
//                line=bufferReader.readLine();
//            } while(line.compareTo(CLOSE)!=0);

        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Socket read Error");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            assert inputSocket != null;
            inputSocket.close();
            assert outputSocket != null;
            outputSocket.close();
            bufferReader.close();
            socket.close();
            System.out.println("Connection Closed");
        }
    }

    private static void initIO() throws IOException {
        try {
            socket = new Socket(address, PORT);
            bufferReader = new BufferedReader(new InputStreamReader(System.in));
            inputSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputSocket = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.print("IO Exception");
        }
    }

    private static void waitForServer() throws IOException {
        String line;
        while(busy(line = inputSocket.readLine()))
            System.out.print(line);
    }

    private static Boolean busy(String input) {
        return !input.contains("⏱");
    }

    public int read () {
        //store result in a variable that update will be able to use and add one
        return 1;
    }

    public void update (int input) {
        //code
    }

}