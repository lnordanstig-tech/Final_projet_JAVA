package farm.core;

import farm.crops.Crop;
import farm.exceptions.InvalidPlotException;
import farm.model.Harvestable;
import farm.model.Product;
import farm.model.Season;
import farm.model.Tickable;
import java.util.List;
import java.util.Objects;

public final class Plot implements Tickable, Harvestable {
    private final int number;
    private Crop crop;

    public Plot(int number) {
        if (number < 1) {
            throw new IllegalArgumentException("Plot numbers start at one.");
        }
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public boolean isEmpty() {
        return crop == null;
    }

    public void plant(Crop newCrop) throws InvalidPlotException {
        if (!isEmpty()) {
            throw new InvalidPlotException("Plot " + number + " already contains a crop.");
        }
        crop = Objects.requireNonNull(newCrop);
    }

    public void water() throws InvalidPlotException {
        if (isEmpty()) {
            throw new InvalidPlotException("Plot " + number + " is empty.");
        }
        crop.water();
    }

    public void prepareSeason(Season season) {
        if (!isEmpty()) {
            crop.setSeason(season);
        }
    }

    @Override
    public void onNewDay() {
        if (!isEmpty()) {
            crop.onNewDay();
        }
    }

    @Override
    public boolean isReady() {
        return !isEmpty() && crop.isReady();
    }

    @Override
    public List<Product> harvest() {
        if (!isReady()) {
            return List.of();
        }
        List<Product> products = crop.harvest();
        crop = null;
        return products;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "Plot " + number + ": empty";
        }
        return "Plot " + number + ": " + crop.toString();
    }
}
