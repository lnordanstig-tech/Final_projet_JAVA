package farm.crops;

import java.util.Locale;

public enum CropType {
    MANDRAKE("mandrake", 8),
    CARROT("carrot", 12),
    PUMPKIN("pumpkin", 20),
    GILLYWEED("gillyweed", 30);

    private final String name;
    private final int seedPrice;

    CropType(String name, int seedPrice) {
        this.name = name;
        this.seedPrice = seedPrice;
    }

    public String getName() {
        return name;
    }

    public int getSeedPrice() {
        return seedPrice;
    }

    public Crop createCrop() {
        switch (this) {
            case MANDRAKE:
                return new Mandrake();
            case CARROT:
                return new Carrot();
            case PUMPKIN:
                return new Pumpkin();
            case GILLYWEED:
                return new Gillyweed();
            default:
                throw new IllegalStateException("Unknown crop type.");
        }
    }

    public static CropType fromName(String name) {
        if (name == null) {
            return null;
        }

        String cropName = name.toLowerCase(Locale.ROOT);
        for (CropType cropType : values()) {
            if (cropType.getName().equals(cropName)) {
                return cropType;
            }
        }
        return null;
    }
}
