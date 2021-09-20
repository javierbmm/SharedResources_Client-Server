package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Operator {
    private Type type;
    private PrintWriter channelOut;
    private BufferedReader channelIn;
    private final static String
        READ    = "READ",
        UPDATE  = "UPDATE",
        CLOSE   = "CLOSE";

    public Operator() {
        this.type = Type.UPDATER;
    }

    public Operator(Type type, PrintWriter channelOut, BufferedReader channelIn) {
        this.type = type;
        this.channelOut = channelOut;
        this.channelIn = channelIn;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Operator setChannelOut(PrintWriter channelOut) {
        this.channelOut = channelOut;
        return this;
    }

    public Operator setChannelIn(BufferedReader channelIn) {
        this.channelIn = channelIn;
        return this;
    }

    public void talk() throws InterruptedException, IOException {
        if (this.type == Type.READONLY)
            doReadOnly();
        else
            doReadUpdate();

        close();
    }

    public void doReadOnly() throws InterruptedException, IOException {
        for(int i=0; i<10; i++) {
            read();
            out();
            Thread.sleep(1000);
        }
    }

    public void doReadUpdate() throws InterruptedException, IOException {
        for(int i=0; i<10; i++) {
            read();
            out();
            update();
            out();
            Thread.sleep(1000);
        }
    }

    private void read() {
        channelOut.println(READ);
        channelOut.flush();
    }

    private void update() {
        channelOut.println(UPDATE);
        channelOut.flush();
    }

    private void close() {
        channelOut.println(CLOSE);
        channelOut.flush();
    }

    private void out() throws IOException {
        System.out.println(channelIn.readLine());
    }

}
