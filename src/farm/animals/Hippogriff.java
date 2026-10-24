package farm.animals;

import farm.model.Product;
import farm.model.ProductType;

public final class Hippogriff extends Animal {
    private static final int BUY_PRICE = 180;
    private static final int PRODUCTION_CYCLE = 2;

    public Hippogriff() {
        super("hippogriff", BUY_PRICE, PRODUCTION_CYCLE);
    }

    @Override
    protected Product produce() {
        return new Product(ProductType.MILK);
    }
}
