package farm.core;

import farm.exceptions.InventoryException;
import farm.model.Sellable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class Inventory<T extends Sellable> {
    private final Map<T, Integer> quantities = new LinkedHashMap<>();

    public int getQuantity(T item) {
        Integer quantity = quantities.get(item);
        if (quantity == null) {
            return 0;
        }
        return quantity;
    }

    public Map<T, Integer> getItems() {
        Map<T, Integer> items = new LinkedHashMap<>(quantities);
        return Collections.unmodifiableMap(items);
    }

    public T findByName(String name) {
        for (T item : quantities.keySet()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public void ensureCanAdd(T item, int quantity) throws InventoryException {
        Objects.requireNonNull(item);
        requirePositive(quantity);
        long newQuantity = (long) getQuantity(item) + quantity;
        if (newQuantity > Integer.MAX_VALUE) {
            throw new InventoryException("Too many " + item.getName() + " in inventory.");
        }
    }

    public void add(T item, int quantity) throws InventoryException {
        ensureCanAdd(item, quantity);
        int newQuantity = getQuantity(item) + quantity;
        quantities.put(item, newQuantity);
    }

    public void addAll(List<? extends T> items) throws InventoryException {
        Map<T, Integer> additions = new LinkedHashMap<>();
        for (T item : items) {
            Objects.requireNonNull(item);
            int quantity = additions.getOrDefault(item, 0);
            additions.put(item, quantity + 1);
        }

        for (Map.Entry<T, Integer> entry : additions.entrySet()) {
            ensureCanAdd(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<T, Integer> entry : additions.entrySet()) {
            add(entry.getKey(), entry.getValue());
        }
    }

    public void remove(T item, int quantity) throws InventoryException {
        requirePositive(quantity);
        int available = getQuantity(item);
        if (available < quantity) {
            throw new InventoryException("Need " + quantity + " " + item.getName()
                    + "; inventory contains " + available + ".");
        }

        int remaining = available - quantity;
        if (remaining == 0) {
            quantities.remove(item);
        } else {
            quantities.put(item, remaining);
        }
    }

    private void requirePositive(int quantity) throws InventoryException {
        if (quantity < 1) {
            throw new InventoryException("Quantity must be a positive whole number.");
        }
    }
}
