package farm.crops;

import farm.model.ProductType;
import farm.model.Season;

public final class Carrot extends Crop {
    private static final int DAYS_TO_GROW = 4;
    private static final int YIELD = 2;
    private static final Season PREFERRED_SEASON = Season.AUTUMN;

    public Carrot() {
        super(ProductType.CARROT, DAYS_TO_GROW);
    }

    @Override
    protected int getYield() {
        return YIELD;
    }

    @Override
    protected int getSeasonModifier() {
        return getSeason().growthBonusFor(PREFERRED_SEASON);
    }
}
