package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.ArrayList;
import java.util.List;
import store.contant.Constant;
import store.dto.BuyDTO;
import store.dto.ProductsDTO;
import store.dto.PurchaseDTO;
import store.dto.ReceiptDTO;
import store.dto.StockDTO;
import store.exception.CustomException;
import store.exception.ErrorMessage;
import store.view.InputView;

public class Products {
    private static final int ZERO = 0;
    private static final int MINIMUM_STOCK = 1;

    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    public ReceiptDTO buyProduct(List<ProductsDTO> items) {
        List<PurchaseDTO> purchaseItem = new ArrayList<>();
        List<PurchaseDTO> freeItem = new ArrayList<>();
        addReceipt(items, purchaseItem, freeItem);
        return new ReceiptDTO(purchaseItem, freeItem);
    }

    private void addReceipt(List<ProductsDTO> items, List<PurchaseDTO> purchaseItem, List<PurchaseDTO> freeItem) {
        for (ProductsDTO productsDTO : items) {
            buy(
                    productsDTO.name(),
                    productsDTO.quantity(),
                    productsDTO.with(),
                    productsDTO.without(),
                    purchaseItem,
                    freeItem
            );
        }
    }

    public List<ProductsDTO> getSameProducts() {
        while (true) {
            try {
                List<BuyDTO> shopping = InputView.readItem();
                return changeProductsDTO(shopping);
            } catch (CustomException e) {
                System.out.println(e.getErrorMessage());
            }
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<ProductsDTO> changeProductsDTO(List<BuyDTO> shopping) {
        return shopping.stream()
                .map(find -> {
                    Product with = getItemWithPromotion(find.name());
                    Product without = getItemWithoutPromotion(find.name());
                    return new ProductsDTO(find.name(), find.quantity(), with, without);
                }).toList();
    }

    private void buy(
            String name, int quantity,
            Product with, Product without, List<PurchaseDTO> purchases, List<PurchaseDTO> frees
    ) {
        if (hasNullPromotion(purchases, with, without, quantity, name)) {
            return;
        }
        quantity = fillQuantity(quantity, with, name);
        StockDTO stockDTO = getPurchaseState(with, quantity, without, name);
        addCart(name, purchases, frees, with, stockDTO);
    }

    private void addCart(
            String name,
            List<PurchaseDTO> purchases, List<PurchaseDTO> frees,
            Product withPromotion, StockDTO stockDTO
    ) {
        purchases.add(new PurchaseDTO(
                name, withPromotion.getPrice(), stockDTO.getTotalStock(), stockDTO.freeStock(), stockDTO.remainStock()));
        frees.add(new PurchaseDTO(
                name, withPromotion.getPrice(), stockDTO.freeStock(), 0, 0));
    }

    private boolean hasNullPromotion(
            List<PurchaseDTO> purchaseItem,
            Product withPromotion, Product withoutPromotion,
            int quantity, String name
    ) {
        if (isInvalidPromotion(withPromotion)) {
            withoutPromotion.notApplyClearanceStock(quantity);
            purchaseItem.add(new PurchaseDTO(name, withoutPromotion.getPrice(), quantity, 0, quantity));
            return true;
        }
        return false;
    }

    private StockDTO getPurchaseState(Product withPromotion, int quantity, Product withoutPromotion, String name) {
        StockDTO stockDTO = withPromotion.getPurchaseState(quantity);
        return clearance(withPromotion, withoutPromotion, name, quantity, stockDTO);
    }

    private int fillQuantity(int quantity, Product withPromotion, String name) {
        if (isNotApplyPromotion(quantity, withPromotion)) {
            int addition = withPromotion.getPromotion().getApplyPromotion() - quantity;
            if (hasGetOneFree(name, addition)) {
                quantity += addition;
            }
        }
        return quantity;
    }

    private StockDTO clearance(
            Product with, Product without,
            String name, int quantity, StockDTO stockDTO
    ) {
        if (with.getStock() < quantity) {
            if (hasApplyPromotion(name, stockDTO)) {
                return nonApplyPromotionItem(with, without, stockDTO);
            }
            return onlyPromotionItem(with, without, stockDTO);
        }
        return applyPromotion(with, quantity, stockDTO);
    }

    private StockDTO applyPromotion(Product with, int quantity, StockDTO stockDTO) {
        with.applyClearanceStock(quantity);
        return new StockDTO(0, stockDTO.applyStock(), stockDTO.freeStock());
    }

    private StockDTO onlyPromotionItem(Product with, Product without, StockDTO stockDTO) {
        applyPromotionClearance(with, without, stockDTO.applyStock() + stockDTO.freeStock());
        return new StockDTO(0, stockDTO.applyStock(), stockDTO.freeStock());
    }

    private StockDTO nonApplyPromotionItem(Product with, Product without, StockDTO stockDTO) {
        applyPromotionClearance(with, without, stockDTO.getTotalStock());
        return new StockDTO(stockDTO.remainStock(), stockDTO.applyStock(), stockDTO.freeStock());
    }

    private void applyPromotionClearance(Product withPromotion, Product withoutPromotion, int quantity) {
        validateQuantity(quantity);
        int remain = withPromotion.applyClearanceStock(quantity);
        if (isRemainStock(remain)) {
            withoutPromotion.notApplyClearanceStock(remain);
        }
    }

    private boolean isRemainStock(int remain) {
        return remain > ZERO;
    }

    private Product getItemWithPromotion(String name) {
        return products.stream()
                .filter(product -> product.isSameName(name) && product.isWithPromotion())
                .findFirst()
                .orElse(null);
    }

    private Product getItemWithoutPromotion(String name) {
        return products.stream()
                .filter(product -> product.isSameName(name) && !product.isWithPromotion())
                .findFirst()
                .orElse(null);
    }

    private void validateQuantity(int quantity) {
        if (quantity < MINIMUM_STOCK) {
            throw new CustomException(ErrorMessage.QUANTITY_OVER_MESSAGE.toString());
        }
    }

    private boolean hasGetOneFree(String name, int addition) {
        return InputView.readGetOneFree(name, addition).equals(Constant.Y.toString());
    }

    private boolean hasApplyPromotion(String name, StockDTO stockDTO) {
        return InputView.readPromotion(name, stockDTO.remainStock()).equals(Constant.Y.toString());
    }

    private boolean isInvalidPromotion(Product withPromotion) {
        return withPromotion == null || withPromotion.getPromotion().isLastDate(DateTimes.now());
    }

    private boolean isNotApplyPromotion(int quantity, Product withPromotion) {
        return quantity < withPromotion.getPromotion().getApplyPromotion();
    }
}
