package farm.model;

import farm.crops.Crop;
import farm.crops.CropType;
import java.util.Objects;

public final class Seed extends Item implements Purchasable {
    private final CropType cropType;

    public Seed(CropType cropType) {
        super(Objects.requireNonNull(cropType).getName() + "-seed",
                "Plant one seed in an empty plot.", cropType.getSeedPrice() / 2);
        this.cropType = cropType;
    }

    @Override
    public int getBuyPrice() {
        return cropType.getSeedPrice();
    }

    public CropType getCropType() {
        return cropType;
    }

    public Crop createCrop() {
        return cropType.createCrop();
    }

    @Override
    public String getCategory() {
        return "seed";
    }
}
