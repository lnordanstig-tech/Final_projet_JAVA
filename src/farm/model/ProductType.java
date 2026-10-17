package farm.model;

public enum ProductType {
    MANDRAKE("mandrake", 16),
    CARROT("carrot", 24),
    PUMPKIN("pumpkin", 65),
    GILLYWEED("gillyweed", 100),
    EGG("ashwinder-egg", 12),
    MILK("milk", 30),
    WOOL("never-tangle-wool", 48);

    private final String name;
    private final int sellPrice;

    ProductType(String name, int sellPrice) {
        this.name = name;
        this.sellPrice = sellPrice;
    }

    public String getName() {
        return name;
    }

    public int getSellPrice() {
        return sellPrice;
    }
}
