package farm.core;

import farm.animals.Ashwinder;
import farm.crops.CropType;
import farm.exceptions.GameException;
import farm.exceptions.InvalidPlotException;
import farm.exceptions.InventoryException;
import farm.model.Feed;
import farm.model.Item;
import farm.model.Product;
import farm.model.Seed;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Game {
    public static final int STARTING_GOLD = 100;
    public static final int TARGET_GOLD = 1000;
    public static final int MAXIMUM_DAYS = 60;
    public static final int PLOT_COUNT = 6;
    public static final int PEN_CAPACITY = 6;
    public static final int STARTING_FEED = 10;

    private final Player player;
    private final Farm farm;
    private final Market market;
    private final Calendar calendar;
    private final int targetGold;
    private GameState state = GameState.RUNNING;

    public Game() {
        this(STARTING_GOLD, TARGET_GOLD, MAXIMUM_DAYS);
    }

    public Game(int startingGold, int targetGold, int maximumDays) {
        if (targetGold < 1) {
            throw new IllegalArgumentException("The gold must be positive.");
        }
        this.targetGold = targetGold;
        player = new Player(startingGold);
        farm = new Farm(PLOT_COUNT, PEN_CAPACITY);
        market = new Market();
        calendar = new Calendar(maximumDays);
        try {
            player.getInventory().add(new Seed(CropType.MANDRAKE), 2);
            player.getInventory().add(new Seed(CropType.CARROT), 2);
            player.getInventory().add(new Seed(CropType.PUMPKIN), 1);
            player.getInventory().add(new Seed(CropType.GILLYWEED), 1);
            player.getInventory().add(new Feed(), STARTING_FEED);
            farm.addAnimal(new Ashwinder());
        } catch (InventoryException exception) {
            throw new IllegalStateException("Invalid way to start farm configuration.", exception);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Farm getFarm() {
        return farm;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public GameState getState() {
        return state;
    }

    public int getTargetGold() {
        return targetGold;
    }

    public String plant(int plotNumber, String cropName) throws GameException {
        ensureRunning();
        CropType cropType = CropType.fromName(cropName);
        if (cropType == null) {
            throw new InventoryException("Unknown crop: " + cropName + ".");
        }
        Plot plot = farm.getPlot(plotNumber);
        if (!plot.isEmpty()) {
            throw new InvalidPlotException("Plot " + plotNumber + " already contains a crop.");
        }
        Seed seed = new Seed(cropType);
        player.getInventory().remove(seed, 1);
        plot.plant(seed.createCrop());
        return "Planted " + cropType.getName() + " in plot " + plotNumber + ".";
    }

    public String water(int plotNumber) throws GameException {
        ensureRunning();
        farm.getPlot(plotNumber).water();
        return "Watered plot " + plotNumber + ".";
    }

    public String harvest(int plotNumber) throws GameException {
        ensureRunning();
        Plot plot = farm.getPlot(plotNumber);
        if (!plot.isReady()) {
            throw new InvalidPlotException("Plot " + plotNumber + " has no ready crop.");
        }
        List<Product> products = plot.harvest();
        player.getInventory().addAll(products);
        return "Collected " + products.size() + " product from plot " + plotNumber + ".";
    }

    public String harvestAll() throws GameException {
        ensureRunning();
        List<Product> products = farm.harvestAll();
        player.getInventory().addAll(products);
        if (products.isEmpty()) {
            return "Nothing is ready to harvest.";
        }
        return "Collected " + products.size() + " product from crops and animals.";
    }

    public String buy(String itemName, int quantity) throws GameException {
        ensureRunning();
        int cost = market.buy(itemName, quantity, player, farm);
        return "Bought " + quantity + " " + itemName + " for " + cost + " gold.";
    }

    public String sell(String itemName, int quantity) throws GameException {
        ensureRunning();
        int proceeds = market.sell(itemName, quantity, player);
        return "Sold " + quantity + " " + itemName + " for " + proceeds + " gold.";
    }

    public String feed() throws GameException {
        ensureRunning();
        int fedAnimals = farm.feedAnimals(player.getInventory());
        if (fedAnimals == 0) {
            return "All animals have already been fed today.";
        }
        return "Fed " + fedAnimals + " animal.";
    }

    public String nextDay() throws GameException {
        ensureRunning();
        int endedDay = calendar.getDay();
        farm.advanceDay(calendar.getSeason());
        calendar.advance();
        if (player.getGold() >= targetGold) {
            state = GameState.WON;
            return "You won with " + player.getGold() + " gold after " + endedDay + " days!";
        }
        if (calendar.isFinished()) {
            state = GameState.LOST;
            return "You lost: all " + calendar.getMaximumDays() + " days are over. Gold: "
                    + player.getGold() + "/" + targetGold + ".";
        }
        return "Day " + endedDay + " ended. Day " + calendar.getDay() + " begins ("
                + calendar.getSeason() + "). Use status to check your farm.";
    }

    public void quit() {
        if (state == GameState.RUNNING) {
            state = GameState.QUIT;
        }
    }

    public List<String> getStatusLines() {
        List<String> lines = new ArrayList<>();
        lines.add("Day " + calendar.getDay() + "/" + calendar.getMaximumDays()
                + " | " + calendar.getSeason() + " | gold " + player.getGold()
                + "/" + targetGold + " | state " + state);
        lines.addAll(farm.getPlotDescriptions());
        lines.add("Animals: " + farm.getAnimalCount() + "/" + farm.getPenCapacity());
        lines.addAll(farm.getAnimalDescriptions());
        lines.add("Inventory:");
        Map<Item, Integer> items = player.getInventory().getItems();
        if (items.isEmpty()) {
            lines.add("  empty");
        } else {
            for (Map.Entry<Item, Integer> entry : items.entrySet()) {
                Item item = entry.getKey();
                int quantity = entry.getValue();
                lines.add("  " + item.getName() + " x" + quantity
                        + " (sell: " + item.getSellPrice() + " gold each)");
            }
        }
        return List.copyOf(lines);
    }

    public List<String> getMarketLines() {
        return market.getCatalog();
    }

    private void ensureRunning() throws InventoryException {
        if (state != GameState.RUNNING) {
            throw new InventoryException("The game has ended, Hogwarts is sleeping. Start a new game to keep farming.");
        }
    }
}
