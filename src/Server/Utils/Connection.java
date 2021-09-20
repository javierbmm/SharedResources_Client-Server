package Server.Utils;

public class Connection {
    private final int id;
    private volatile Boolean locked;
    private volatile Boolean closed;
    public Connection(int id) {
        this.id = id;
        this.locked = true;
        this.closed = false;
    }

    public int getId() {
        return id;
    }

    public Boolean isLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean isClosed() {
        return closed;
    }

    public void close() {
        this.closed = true;
    }

    public Connection free() {
        this.setLocked(false);
        return this;
    }
}
