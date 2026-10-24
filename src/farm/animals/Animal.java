package farm.animals;

import farm.model.Harvestable;
import farm.model.Item;
import farm.model.Product;
import farm.model.Purchasable;
import farm.model.Tickable;
import java.util.List;

public abstract class Animal extends Item implements Tickable, Harvestable, Purchasable {
    private static final int MAX_HAPPINESS = 3;
    private static final int MAX_HUNGER = 3;
    private static final int MIN_PRODUCTIVE_HAPPINESS = 1;

    private final int buyPrice;
    private final int productionCycle;
    private int hunger;
    private int happiness = MAX_HAPPINESS;
    private int productionProgress;
    private boolean fedToday;
    private boolean ready;

    protected Animal(String name, int buyPrice, int productionCycle) {
        super(name, "A Hogwart animal that needs daily feeding.", buyPrice / 2);
        if (buyPrice < 1 || productionCycle < 1) {
            throw new IllegalArgumentException("Animal price and cycle must be positive.");
        }
        this.buyPrice = buyPrice;
        this.productionCycle = productionCycle;
    }

    public final int getBuyPrice() {
        return buyPrice;
    }

    public final String getCategory() {
        return "animal";
    }

    public final int getHunger() {
        return hunger;
    }

    public final int getHappiness() {
        return happiness;
    }

    public final boolean isFedToday() {
        return fedToday;
    }

    public final void feed() {
        if (!fedToday) {
            fedToday = true;
            hunger = 0;
            happiness = Math.min(MAX_HAPPINESS, happiness + 1);
        }
    }

    protected abstract Product produce();

    @Override
    public final void onNewDay() {
        if (fedToday) {
            if (!ready && happiness >= MIN_PRODUCTIVE_HAPPINESS) {
                productionProgress++;
                if (productionProgress >= productionCycle) {
                    ready = true;
                }
            }
        } else {
            hunger = Math.min(MAX_HUNGER, hunger + 1);
            happiness = Math.max(0, happiness - 1);
        }
        fedToday = false;
    }

    @Override
    public final boolean isReady() {
        return ready;
    }

    @Override
    public final List<Product> harvest() {
        if (!ready) {
            return List.of();
        }
        Product product = produce();
        ready = false;
        productionProgress = 0;
        return List.of(product);
    }

    @Override
    public final String toString() {
        String feedingStatus = "needs feed";
        if (fedToday) {
            feedingStatus = "fed";
        }
        String productionStatus = "";
        if (ready) {
            productionStatus = ", ready";
        }
        return getName() + ": " + feedingStatus
                + ", happiness " + happiness + "/" + MAX_HAPPINESS
                + ", hunger " + hunger + "/" + MAX_HUNGER
                + ", production " + productionProgress + "/" + productionCycle
                + productionStatus;
    }
}
