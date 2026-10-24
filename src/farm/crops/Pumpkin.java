package farm.crops;

import farm.model.ProductType;
import farm.model.Season;

public final class Pumpkin extends Crop {
    private static final int DAYS_TO_GROW = 6;
    private static final int YIELD = 1;
    private static final Season PREFERRED_SEASON = Season.AUTUMN;

    public Pumpkin() {
        super(ProductType.PUMPKIN, DAYS_TO_GROW);
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
