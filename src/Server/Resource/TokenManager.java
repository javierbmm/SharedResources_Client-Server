package Server.Resource;

import static Server.Utils.Constants.*;

public class TokenManager {
    private volatile Token token;
    private final String ERROR = "ERROR: Wrong operation. It must be READ or UPDATE.";
    private volatile Boolean busy;

    public TokenManager(int value) {
        this.token = new Token(value);
    }

    public String handle(String operation) {
        if(operation.compareTo(UPDATE) == 0)
            return Integer.toString(token.increment().getValue());
        else if(operation.compareTo(READ) == 0)
            return Integer.toString(token.getValue());
        else if(operation.compareTo(CLOSE) == 0) {
            this.busy = false;
            return "Closing connection...";
        } else
            return ERROR;
    }

}
