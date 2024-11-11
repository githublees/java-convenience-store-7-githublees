package store.dto;

import java.util.List;

public record ReceiptDTO(
        List<PurchaseDTO> cart,
        List<PurchaseDTO> free
) {
    public int getTotalQuantity() {
        return cart.stream()
                .mapToInt(PurchaseDTO::quantity)
                .sum();
    }

    public int getTotalAmount() {
        return cart.stream()
                .mapToInt(PurchaseDTO::getTotalAmount)
                .sum();
    }

    public int getFreeAmount() {
        return free.stream()
                .mapToInt(PurchaseDTO::getTotalAmount)
                .sum();
    }

    public int getMembershipAmount(boolean isMembership) {
        if (isMembership) {
            return (int) (cart.stream()
                                .mapToInt(PurchaseDTO::getNotApplyAmount)
                                .sum() * 0.3);
        }
        return 0;
    }
}
