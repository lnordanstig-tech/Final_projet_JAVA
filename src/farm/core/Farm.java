package farm.core;

import farm.animals.Animal;
import farm.exceptions.InvalidPlotException;
import farm.exceptions.InventoryException;
import farm.model.Feed;
import farm.model.Harvestable;
import farm.model.Item;
import farm.model.Product;
import farm.model.Season;
import farm.model.Tickable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Farm {
    private final List<Plot> plots = new ArrayList<>();
    private final List<Animal> animals = new ArrayList<>();
    private final int penCapacity;

    public Farm(int plotCount, int penCapacity) {
        if (plotCount < 1 || penCapacity < 1) {
            throw new IllegalArgumentException("A farm in Hogwarts needs plots and animal capacity.");
        }
        this.penCapacity = penCapacity;
        for (int number = 1; number <= plotCount; number++) {
            plots.add(new Plot(number));
        }
    }

    public List<String> getPlotDescriptions() {
        List<String> descriptions = new ArrayList<>();
        for (Plot plot : plots) {
            descriptions.add(plot.toString());
        }
        return List.copyOf(descriptions);
    }

    public List<String> getAnimalDescriptions() {
        List<String> descriptions = new ArrayList<>();
        for (Animal animal : animals) {
            descriptions.add(animal.toString());
        }
        return List.copyOf(descriptions);
    }

    public int getAnimalCount() {
        return animals.size();
    }

    public int getPenCapacity() {
        return penCapacity;
    }

    public Plot getPlot(int number) throws InvalidPlotException {
        if (number < 1 || number > plots.size()) {
            throw new InvalidPlotException("Plot must be between 1 and " + plots.size() + ".");
        }
        return plots.get(number - 1);
    }

    public void ensureAnimalCapacity(int quantity) throws InventoryException {
        if (quantity < 1 || (long) animals.size() + quantity > penCapacity) {
            throw new InventoryException("Animal pens have " + (penCapacity - animals.size())
                    + " free spaces.");
        }
    }

    public void addAnimal(Animal animal) throws InventoryException {
        ensureAnimalCapacity(1);
        animals.add(Objects.requireNonNull(animal));
    }

    public int feedAnimals(Inventory<Item> inventory) throws InventoryException {
        List<Animal> unfedAnimals = new ArrayList<>();
        for (Animal animal : animals) {
            if (!animal.isFedToday()) {
                unfedAnimals.add(animal);
            }
        }

        if (!unfedAnimals.isEmpty()) {
            inventory.remove(new Feed(), unfedAnimals.size());
            for (Animal animal : unfedAnimals) {
                animal.feed();
            }
        }
        return unfedAnimals.size();
    }

    public void advanceDay(Season season) {
        for (Plot plot : plots) {
            plot.prepareSeason(season);
        }

        List<Tickable> farmObjects = new ArrayList<>();
        farmObjects.addAll(plots);
        farmObjects.addAll(animals);
        for (Tickable farmObject : farmObjects) {
            farmObject.onNewDay();
        }
    }

    public List<Product> harvestAll() {
        List<Harvestable> harvestableObjects = new ArrayList<>();
        harvestableObjects.addAll(plots);
        harvestableObjects.addAll(animals);

        List<Product> products = new ArrayList<>();
        for (Harvestable harvestable : harvestableObjects) {
            if (harvestable.isReady()) {
                products.addAll(harvestable.harvest());
            }
        }
        return List.copyOf(products);
    }
}
