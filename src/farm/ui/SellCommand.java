package farm.ui;

import farm.core.Game;
import farm.exceptions.GameException;

public final class SellCommand implements Command {
    private final String itemName;
    private final int quantity;

    public SellCommand(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }

    @Override
    public void execute(Game game, ConsoleUI ui) throws GameException {
        ui.println(game.sell(itemName, quantity));
    }
}
