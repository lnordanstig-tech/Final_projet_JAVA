package farm.ui;

import farm.core.Game;
import farm.exceptions.GameException;

public final class PlantCommand implements Command {
    private final int plotNumber;
    private final String cropName;

    public PlantCommand(int plotNumber, String cropName) {
        this.plotNumber = plotNumber;
        this.cropName = cropName;
    }

    @Override
    public void execute(Game game, ConsoleUI ui) throws GameException {
        ui.println(game.plant(plotNumber, cropName));
    }
}
