package farm.model;

import java.util.Objects;

public abstract class Item implements Sellable {
    private final String name;
    private final String description;
    private final int basePrice;

    protected Item(String name, String description, int basePrice) {
        if (name == null || name.isBlank() || description == null || basePrice < 0) {
            throw new IllegalArgumentException("An item needs a name and a non-negative price.");
        }
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
    }

    public final String getName() {
        return name;
    }

    public final String getDescription() {
        return description;
    }

    public final int getSellPrice() {
        return basePrice;
    }

    public abstract String getCategory();

    @Override
    public final boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Item item = (Item) other;
        return name.equals(item.name);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getClass(), name);
    }

    @Override
    public String toString() {
        return name;
    }
}
