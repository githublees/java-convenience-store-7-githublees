package store.dto;

public record PurchaseDTO(
        String name,
        int price,
        int quantity,
        int freeQuantity,
        int remainQuantity
) {

    public int getTotalAmount() {
        return quantity * price;
    }

    public int getNotApplyAmount() {
        return remainQuantity * price;
    }
}
