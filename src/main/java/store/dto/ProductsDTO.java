package store.dto;

import store.domain.Product;
import store.exception.CustomException;
import store.exception.ErrorMessage;

public record ProductsDTO(
        String name,
        int quantity,
        Product with,
        Product without
) {
    public ProductsDTO {
        validateExistProduct(with, without);
    }

    private void validateExistProduct(Product withPromotion, Product withoutPromotion) {
        if (isNotExistProduct(withPromotion, withoutPromotion)) {
            throw new CustomException(ErrorMessage.NOT_EXIST_PRODUCT_MESSAGE.toString());
        }
    }

    private static boolean isNotExistProduct(Product withPromotion, Product withoutPromotion) {
        return withPromotion == null && withoutPromotion == null;
    }
}
