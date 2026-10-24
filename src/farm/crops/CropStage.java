package farm.crops;

public enum CropStage {
    SEEDLING("seedling"),
    GROWING("growing"),
    MATURE("mature"),
    HARVESTED("harvested");

    private final String label;

    CropStage(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
