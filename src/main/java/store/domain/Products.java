package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Products {
    private final List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getProducts() {
        return items;
    }
}
