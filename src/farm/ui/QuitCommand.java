package farm.ui;

import farm.core.Game;

public final class QuitCommand implements Command {
    @Override
    public void execute(Game game, ConsoleUI ui) {
        game.quit();
        ui.println("Thanks for playing our Harry Potter Farm game!");
    }
}
