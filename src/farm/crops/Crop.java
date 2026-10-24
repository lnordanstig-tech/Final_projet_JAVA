package farm.crops;

import farm.model.Harvestable;
import farm.model.Product;
import farm.model.ProductType;
import farm.model.Season;
import farm.model.Tickable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Crop implements Tickable, Harvestable {
    private final ProductType productType;
    private final int daysToGrow;
    private int growth;
    private boolean watered;
    private boolean harvested;
    private Season season = Season.SPRING;

    protected Crop(ProductType productType, int daysToGrow) {
        if (daysToGrow < 1) {
            throw new IllegalArgumentException("Growth duration must be positive.");
        }
        this.productType = Objects.requireNonNull(productType);
        this.daysToGrow = daysToGrow;
    }

    public final String getName() {
        return productType.getName();
    }

    public final int getDaysToGrow() {
        return daysToGrow;
    }

    public final int getGrowth() {
        return growth;
    }

    public final boolean isWatered() {
        return watered;
    }

    public final void water() {
        watered = true;
    }

    public final void setSeason(Season season) {
        this.season = Objects.requireNonNull(season);
    }

    protected final Season getSeason() {
        return season;
    }

    protected abstract int getYield();

    protected abstract int getSeasonModifier();

    @Override
    public final void onNewDay() {
        if (watered && !isReady() && !harvested) {
            growth = Math.min(daysToGrow, growth + 1 + getSeasonModifier());
        }
        watered = false;
    }

    public final CropStage getStage() {
        if (harvested) {
            return CropStage.HARVESTED;
        }
        if (isReady()) {
            return CropStage.MATURE;
        }
        if (growth == 0) {
            return CropStage.SEEDLING;
        }
        return CropStage.GROWING;
    }

    @Override
    public final boolean isReady() {
        return !harvested && growth >= daysToGrow;
    }

    @Override
    public final List<Product> harvest() {
        if (!isReady()) {
            return List.of();
        }
        List<Product> products = new ArrayList<>();
        int productCount = getYield();
        for (int productNumber = 0; productNumber < productCount; productNumber++) {
            products.add(new Product(productType));
        }
        harvested = true;
        return List.copyOf(products);
    }

    @Override
    public final String toString() {
        String waterStatus = ", dry";
        if (watered) {
            waterStatus = ", watered";
        }
        return getName() + " " + getStage() + " (" + growth + "/" + daysToGrow + ")"
                + waterStatus;
    }
}
