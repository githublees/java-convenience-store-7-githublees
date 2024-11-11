package store.exception;

public enum ErrorMessage {
    FILE_IOEXCEPTION_MESSAGE("[ERROR] 파일 양식에 맞게 작성돼야 합니다."),
    QUANTITY_OVER_MESSAGE("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    NOT_FIT_THE_FORM_MESSAGE("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    NOT_EXIST_PRODUCT_MESSAGE("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."),
    INVALID_INPUT_MESSAGE("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
