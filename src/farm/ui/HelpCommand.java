package farm.ui;

import farm.core.Game;
import java.util.List;

public final class HelpCommand implements Command {
    @Override
    public void execute(Game game, ConsoleUI ui) {
        ui.printLines(List.of(
                "status                  Show day, gold, farm, and inventory.",
                "plant <plot> <crop>      Example: plant 1 mandrake",
                "water <plot>            Example: water 1 (repeat each day).",
                "harvest <plot|all>      Example: harvest all (includes animals).",
                "buy <item> [quantity]   Example: buy mandrake-seed 2",
                "sell <item> [quantity]  Example: sell ashwinder-egg 3",
                "feed                    Feed every unfed animal, one feed portion each.",
                "next                    End this day; grow crops and produce animal goods.",
                "help                    Show commands and shop prices.",
                "quit                    Leave Hogwarts.",
                "Crops: mandrake, carrot, pumpkin, gillyweed. Plots: 1-6.",
                "Preferred seasons: mandrake in spring, carrot/pumpkin in autumn,",
                "gillyweed in summer. Preferred crops grow twice as fast.",
                "Animals hold one product until harvested, production pauses if unfed.",
                "Reach the gold goal and use next to win before the day limit.",
                "Shop items:"));
        ui.printLines(game.getMarketLines());
    }
}
