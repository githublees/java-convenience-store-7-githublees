package store.view;

import java.text.NumberFormat;
import store.domain.Product;
import store.domain.Products;
import store.dto.PurchaseDTO;
import store.dto.ReceiptDTO;

public class OutputView {
    private static final NumberFormat format = NumberFormat.getNumberInstance();

    public static void printProducts(Products products) {
        System.out.println(OutputMessage.OUTPUT_HELLO_CONVENIENCE_MESSAGE);
        for (Product product : products.getProducts()) {
            String name = product.getName();
            String price = getPrice(product);
            String quantity = getQuantity(product);
            String promotion = getPromotion(product);
            System.out.println("- " + name + " " + price + " " + quantity + " " + promotion);
        }
        System.out.println();
    }

    public static void printReceipt(ReceiptDTO receiptDTO, boolean isMembership) {
        printPurchaseItems(receiptDTO);
        printFreeItems(receiptDTO);
        printAccount(receiptDTO, isMembership);
    }

    private static void printAccount(ReceiptDTO receiptDTO, boolean isMembership) {
        System.out.println(OutputMessage.OUTPUT_RECEIPT_BOTTOM_MESSAGE);
        printTotalPrice(receiptDTO);
        printPromotionDiscount(receiptDTO);
        printMembershipDiscount(receiptDTO, isMembership);
        printPayment(receiptDTO, isMembership);
    }

    private static void printPayment(ReceiptDTO receiptDTO, boolean isMembership) {
        System.out.printf(OutputMessage.OUTPUT_RECEIPT_PAY_MESSAGE.format(
                OutputMessage.TO_BE_PAID,
                format.format(totalPayment(receiptDTO, isMembership))));
        System.out.println();
    }

    private static int totalPayment(ReceiptDTO receiptDTO, boolean isMembership) {
        return receiptDTO.getTotalAmount() - receiptDTO.getFreeAmount() - receiptDTO.getMembershipAmount(isMembership);
    }

    private static void printMembershipDiscount(ReceiptDTO receiptDTO, boolean isMembership) {
        System.out.printf(OutputMessage.OUTPUT_RECEIPT_DISCOUNT_MESSAGE.format(
                OutputMessage.MEMBERSHIP_DISCOUNT,
                "-" + format.format(receiptDTO.getMembershipAmount(isMembership))));
    }

    private static void printPromotionDiscount(ReceiptDTO receiptDTO) {
        System.out.printf(OutputMessage.OUTPUT_RECEIPT_DISCOUNT_MESSAGE.format(
                OutputMessage.PROMOTION_DISCOUNT,
                "-" + format.format(receiptDTO.getFreeAmount())));
    }

    private static void printTotalPrice(ReceiptDTO receiptDTO) {
        System.out.printf(OutputMessage.OUTPUT_RECEIPT_PRODUCT_MESSAGE.format(
                OutputMessage.TOTAL_PRICE,
                receiptDTO.getTotalQuantity(),
                format.format(receiptDTO.getTotalAmount())));
    }

    private static void printFreeItems(ReceiptDTO receiptDTO) {
        System.out.println(OutputMessage.OUTPUT_RECEIPT_MIDDLE_MESSAGE);
        for (PurchaseDTO purchaseDTO : receiptDTO.free()) {
            System.out.printf(OutputMessage.OUTPUT_RECEIPT_FREE_MESSAGE.format(
                    purchaseDTO.name(), purchaseDTO.quantity()));
        }
    }

    private static void printPurchaseItems(ReceiptDTO receiptDTO) {
        System.out.println(OutputMessage.OUTPUT_RECEIPT_TOP_MESSAGE);
        for (PurchaseDTO purchaseDTO : receiptDTO.cart()) {
            System.out.printf(OutputMessage.OUTPUT_RECEIPT_PRODUCT_MESSAGE.format(
                    purchaseDTO.name(),
                    purchaseDTO.quantity(),
                    format.format(purchaseDTO.getTotalAmount()))
            );
        }
    }

    private static String getPrice(Product product) {
        return format.format(product.getPrice()) + OutputMessage.CURRENCY_UNIT;
    }

    private static String getPromotion(Product product) {
        String promotion = "";
        if (product.getPromotion() != null) {
            promotion = product.getPromotion().getName();
        }
        return promotion;
    }

    private static String getQuantity(Product product) {
        String quantity = format.format(product.getStock()) + OutputMessage.QUANTITY_UNIT;
        if (product.getStock() == 0) {
            quantity = OutputMessage.QUANTITY_EMPTY.toString();
        }
        return quantity;
    }
}
