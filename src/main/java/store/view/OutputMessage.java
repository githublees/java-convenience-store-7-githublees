package store.view;

public enum OutputMessage {
    OUTPUT_HELLO_CONVENIENCE_MESSAGE("안녕하세요 W편의점입니다.\n현재 보유하고 있는 상품입니다.\n"),
    CURRENCY_UNIT("원"),
    QUANTITY_UNIT("개"),
    QUANTITY_EMPTY("재고 없음"),
    TOTAL_PRICE("총구매액"),
    PROMOTION_DISCOUNT("행사할인"),
    MEMBERSHIP_DISCOUNT("멤버십할인"),
    TO_BE_PAID("내실돈"),
    OUTPUT_RECEIPT_TOP_MESSAGE("==============W 편의점================\n상품명\t\t\t\t수량\t\t금액"),
    OUTPUT_RECEIPT_MIDDLE_MESSAGE("=============증\t\t정==============="),
    OUTPUT_RECEIPT_BOTTOM_MESSAGE("====================================="),
    OUTPUT_RECEIPT_PRODUCT_MESSAGE("%-15s\t%-6s\t%-3s\n"),
    OUTPUT_RECEIPT_FREE_MESSAGE("%-15s\t%-6s\t\n"),
    OUTPUT_RECEIPT_DISCOUNT_MESSAGE("%-21s\t%-3s\n"),
    OUTPUT_RECEIPT_PAY_MESSAGE("%-23s\t%-3s\n");

    private final String message;

    OutputMessage(String message) {
        this.message = message;
    }

    public String format(Object... args) {
        return String.format(message, args);
    }

    @Override
    public String toString() {
        return message;
    }

}
