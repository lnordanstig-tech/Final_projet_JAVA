package farm.core;

import farm.animals.Animal;
import farm.animals.AnimalType;
import farm.crops.CropType;
import farm.exceptions.GameException;
import farm.exceptions.InventoryException;
import farm.model.Feed;
import farm.model.Item;
import farm.model.Purchasable;
import farm.model.Seed;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class Market {
    private final Map<String, Seed> seeds = new LinkedHashMap<>();
    private final Map<String, AnimalType> animalTypes = new LinkedHashMap<>();
    private final Feed feed = new Feed();

    public Market() {
        for (CropType cropType : CropType.values()) {
            Seed seed = new Seed(cropType);
            seeds.put(seed.getName(), seed);
        }
        for (AnimalType animalType : AnimalType.values()) {
            Animal animal = animalType.createAnimal();
            animalTypes.put(animal.getName(), animalType);
        }
    }

    public List<String> getCatalog() {
        List<String> catalog = new ArrayList<>();
        for (Seed seed : seeds.values()) {
            catalog.add(seed.getName() + ": " + seed.getBuyPrice() + " gold");
        }
        catalog.add(feed.getName() + ": " + feed.getBuyPrice() + " gold");
        for (AnimalType animalType : animalTypes.values()) {
            Animal animal = animalType.createAnimal();
            catalog.add(animal.getName() + ": " + animal.getBuyPrice() + " gold");
        }
        return List.copyOf(catalog);
    }

    public int buy(String name, int quantity, Player player, Farm farm) throws GameException {
        String itemName = name.toLowerCase(Locale.ROOT);
        Seed seed = seeds.get(itemName);
        if (seed != null) {
            int cost = getPurchaseCost(seed, quantity);
            buySupplies(seed, quantity, cost, player);
            return cost;
        }
        if (itemName.equals(feed.getName())) {
            int cost = getPurchaseCost(feed, quantity);
            buySupplies(feed, quantity, cost, player);
            return cost;
        }

        AnimalType animalType = animalTypes.get(itemName);
        if (animalType != null) {
            return buyAnimals(animalType, quantity, player, farm);
        }
        throw new InventoryException("Unknown Hogwarts item: " + name + ". Use help.");
    }

    public int sell(String name, int quantity, Player player) throws InventoryException {
        Item item = player.getInventory().findByName(name);
        if (item == null) {
            throw new InventoryException("No " + name + " in inventory.");
        }

        int proceeds = transactionValue(item.getSellPrice(), quantity);
        player.ensureCanReceive(proceeds);
        player.getInventory().remove(item, quantity);
        player.receive(proceeds);
        return proceeds;
    }

    private void buySupplies(Item item, int quantity, int cost, Player player)
            throws GameException {
        player.ensureCanSpend(cost);
        player.getInventory().add(item, quantity);
        player.spend(cost);
    }

    private int buyAnimals(AnimalType animalType, int quantity, Player player, Farm farm)
            throws GameException {
        Animal animal = animalType.createAnimal();
        int cost = getPurchaseCost(animal, quantity);
        player.ensureCanSpend(cost);
        farm.ensureAnimalCapacity(quantity);

        for (int animalNumber = 0; animalNumber < quantity; animalNumber++) {
            farm.addAnimal(animalType.createAnimal());
        }
        player.spend(cost);
        return cost;
    }

    private int getPurchaseCost(Purchasable item, int quantity) throws InventoryException {
        return transactionValue(item.getBuyPrice(), quantity);
    }

    private int transactionValue(int unitPrice, int quantity) throws InventoryException {
        if (quantity < 1) {
            throw new InventoryException("Quantity must be a positive whole number.");
        }

        long totalPrice = (long) unitPrice * quantity;
        if (totalPrice > Integer.MAX_VALUE) {
            throw new InventoryException("This transaction is too large.");
        }
        return (int) totalPrice;
    }
}
