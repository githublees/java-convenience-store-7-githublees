package store.dto;

public record PurchasesDTO(
        PurchaseDTO purchase,
        PurchaseDTO free
) {
}
