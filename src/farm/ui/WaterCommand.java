package farm.ui;

import farm.core.Game;
import farm.exceptions.GameException;

public final class WaterCommand implements Command {
    private final int plotNumber;

    public WaterCommand(int plotNumber) {
        this.plotNumber = plotNumber;
    }

    @Override
    public void execute(Game game, ConsoleUI ui) throws GameException {
        ui.println(game.water(plotNumber));
    }
}
