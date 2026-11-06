package farm.ui;

import farm.core.Game;
import farm.exceptions.GameException;

public final class NextDayCommand implements Command {
    @Override
    public void execute(Game game, ConsoleUI ui) throws GameException {
        ui.println(game.nextDay());
    }
}
