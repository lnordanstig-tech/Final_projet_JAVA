package farm.model;

import java.util.List;

public interface Harvestable {
    boolean isReady();

    List<Product> harvest();
}
