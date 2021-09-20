package Server.Utils;

import java.util.LinkedList;

public class Queue {
    LinkedList <Connection> connections;

    public Queue() {
        this.connections = new LinkedList<Connection>();
    }

    public LinkedList<Connection> getConnections() {
        return connections;
    }

    public void pushQueue (Connection connection) {
        connections.add(connection);
    }

    public Connection popQueue () {
        Connection var = connections.getFirst();
        shiftLeft();
        return var;
    }

    public void shiftLeft () {
        Connection aux;
        for (int i = 1; i< connections.size(); i++) {
            aux = connections.get(i);
            connections.set(i-1, aux);
        }
    }

    public Boolean isEmpty() {
        return connections.isEmpty();
    }
}
