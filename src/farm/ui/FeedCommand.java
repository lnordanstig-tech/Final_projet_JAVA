package farm.ui;

import farm.core.Game;
import farm.exceptions.GameException;

public final class FeedCommand implements Command {
    @Override
    public void execute(Game game, ConsoleUI ui) throws GameException {
        ui.println(game.feed());
    }
}
