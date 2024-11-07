package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Promotions {
    private final List<Promotion> promotions = new ArrayList<>();

    public void addPromotion(Promotion promotion) {
        promotions.add(promotion);
    }

    public Promotion getPromotion(String name) {
        return promotions.stream()
                .filter(promotion -> promotion.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
