package farm.model;
import java.util.Objects;

public final class Product extends Item {
    private final ProductType type;

    public Product(ProductType type) {
        super(Objects.requireNonNull(type).getName(), "A farm product.", type.getSellPrice());
        this.type = type;
    }

    public ProductType getType() {
        return type;
    }

    @Override
    public String getCategory() {
        return "product";
    }
}
