package farm.model;

public final class Feed extends Item implements Purchasable {
    private static final int BUY_PRICE = 2;
    private static final int SELL_PRICE = 1;

    public Feed() {
        super("feed", "One daily feed portion for one Hogwart animal.", SELL_PRICE);
    }

    @Override
    public int getBuyPrice() {
        return BUY_PRICE;
    }

    @Override
    public String getCategory() {
        return "feed";
    }
}
