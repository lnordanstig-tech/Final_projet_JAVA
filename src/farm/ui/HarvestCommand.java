package farm.ui;

import farm.core.Game;
import farm.exceptions.GameException;

public final class HarvestCommand implements Command {
    private final boolean harvestAll;
    private final int plotNumber;

    public HarvestCommand() {
        harvestAll = true;
        plotNumber = 0;
    }

    public HarvestCommand(int plotNumber) {
        harvestAll = false;
        this.plotNumber = plotNumber;
    }

    @Override
    public void execute(Game game, ConsoleUI ui) throws GameException {
        String message;
        if (harvestAll) {
            message = game.harvestAll();
        } else {
            message = game.harvest(plotNumber);
        }
        ui.println(message);
    }
}
