package farm.core;

import farm.exceptions.InventoryException;
import farm.exceptions.NotEnoughGoldException;
import farm.model.Item;

public final class Player {
    private int gold;
    private final Inventory<Item> inventory = new Inventory<>();

    public Player(int startingGold) {
        if (startingGold < 0) {
            throw new IllegalArgumentException("Starting gold cannot be negative.");
        }
        gold = startingGold;
    }

    public int getGold() {
        return gold;
    }

    public Inventory<Item> getInventory() {
        return inventory;
    }

    public void ensureCanSpend(int amount) throws NotEnoughGoldException {
        if (amount < 0) {
            throw new IllegalArgumentException("Cost cannot be negative.");
        }
        if (gold < amount) {
            throw new NotEnoughGoldException("Need " + amount + " gold. You have " + gold + ".");
        }
    }

    public void spend(int amount) throws NotEnoughGoldException {
        ensureCanSpend(amount);
        gold -= amount;
    }

    public void ensureCanReceive(int amount) throws InventoryException {
        if (amount < 0 || (long) gold + amount > Integer.MAX_VALUE) {
            throw new InventoryException("This sale would exceed the gold limit.");
        }
    }

    public void receive(int amount) throws InventoryException {
        ensureCanReceive(amount);
        gold += amount;
    }
}
