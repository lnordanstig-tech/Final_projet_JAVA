package farm.ui;

import farm.core.Game;

public final class ShowStatusCommand implements Command {
    @Override
    public void execute(Game game, ConsoleUI ui) {
        ui.printLines(game.getStatusLines());
    }
}
