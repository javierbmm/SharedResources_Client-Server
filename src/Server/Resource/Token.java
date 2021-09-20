package Server.Resource;

public class Token {
    private int value;

    public Token(int value) {
        this.value = value;
    }

    public Token increment() {
        return this.setValue(this.getValue() + 1);
    }

    public int getValue() {
        return value;
    }

    public Token setValue(int value) {
        this.value = value;
        return this;
    }
}
