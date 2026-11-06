package farm;

import farm.core.Game;
import farm.ui.ConsoleUI;

public final class Main {
    private Main() {
    }

    public static void main(String[] arguments) {
        Game game = new Game();
        ConsoleUI console = new ConsoleUI();
        console.run(game);
    }
}
