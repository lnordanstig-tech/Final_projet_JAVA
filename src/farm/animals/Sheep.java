package farm.animals;

import farm.model.Product;
import farm.model.ProductType;

public final class Sheep extends Animal {
    private static final int BUY_PRICE = 150;
    private static final int PRODUCTION_CYCLE = 3;

    public Sheep() {
        super("big-horn-sheep", BUY_PRICE, PRODUCTION_CYCLE);
    }

    @Override
    protected Product produce() {
        return new Product(ProductType.WOOL);
    }
}
