package farm.ui;

import farm.core.Game;
import farm.exceptions.GameException;

public final class BuyCommand implements Command {
    private final String itemName;
    private final int quantity;

    public BuyCommand(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }

    @Override
    public void execute(Game game, ConsoleUI ui) throws GameException {
        ui.println(game.buy(itemName, quantity));
    }
}
