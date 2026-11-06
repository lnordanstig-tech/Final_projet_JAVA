package farm.ui;

import farm.core.Game;
import farm.exceptions.GameException;

public interface Command {
    void execute(Game game, ConsoleUI ui) throws GameException;
}
