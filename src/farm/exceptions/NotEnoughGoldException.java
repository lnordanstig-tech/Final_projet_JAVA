package farm.exceptions;

public final class NotEnoughGoldException extends GameException {
    private static final long serialVersionUID = 1L;

    public NotEnoughGoldException(String message) {
        super(message);
    }
}
