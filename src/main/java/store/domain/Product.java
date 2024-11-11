package store.domain;

import store.dto.StockDTO;
import store.exception.CustomException;
import store.exception.ErrorMessage;

public class Product {
    private final String name;
    private final int price;
    private int stock;
    private final Promotion promotion;

    public Product(String name, int price, int stock, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.promotion = promotion;
    }

    public StockDTO getPurchaseState(int quantity) {
        int apply = promotion.getApplyProduct(quantity);;
        int free = promotion.getFreeProduct(quantity);

        if (stock < quantity) {
            apply = promotion.getApplyProduct(stock);
            free = promotion.getFreeProduct(stock);
        }

        return new StockDTO(quantity - (apply + free), apply, free);
    }

    public int applyClearanceStock(int remain) {
        if (stock < remain) {
            remain -= stock;
            stock = 0;
            return remain;
        }
        stock -= remain;
        return 0;
    }

    public void notApplyClearanceStock(int quantity) {
        if (stock < quantity) {
            throw new CustomException(ErrorMessage.QUANTITY_OVER_MESSAGE.toString());
        }
        stock -= quantity;
    }

    public boolean isWithPromotion() {
        return promotion != null;
    }

    public boolean isSameName(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public Promotion getPromotion() {
        return promotion;
    }
}
