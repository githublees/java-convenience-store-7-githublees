package store.dto;

public record StockDTO(
        int remainStock,
        int applyStock,
        int freeStock
) {
    public int getTotalStock() {
        return remainStock + applyStock + freeStock;
    }
}
