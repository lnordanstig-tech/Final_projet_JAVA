package farm.exceptions;

public final class InvalidPlotException extends GameException {
    private static final long serialVersionUID = 1L;

    public InvalidPlotException(String message) {
        super(message);
    }
}
