package farm.exceptions;

public abstract class GameException extends Exception {
    private static final long serialVersionUID = 1L;

    protected GameException(String message) {
        super(message);
    }
}
