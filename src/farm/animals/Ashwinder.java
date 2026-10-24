package farm.animals;

import farm.model.Product;
import farm.model.ProductType;

public final class Ashwinder extends Animal {
    private static final int BUY_PRICE = 60;
    private static final int PRODUCTION_CYCLE = 1;

    public Ashwinder() {
        super("ashwinder", BUY_PRICE, PRODUCTION_CYCLE);
    }

    @Override
    protected Product produce() {
        return new Product(ProductType.EGG);
    }
}
