package farm.exceptions;

public final class InvalidCommandException extends GameException {
    private static final long serialVersionUID = 1L;

    public InvalidCommandException(String message) {
        super(message);
    }
}
