package store.contant;

public enum Constant {
    PRODUCTS_FILE_PATH("./src/main/resources/products.md"),
    PROMOTIONS_FILE_PATH("./src/main/resources/promotions.md"),

    Y("Y"),
    N("N"),

    YEAR("year"),
    MONTH("month"),
    DAY("day"),

    DEFAULT_DELIMITER("-"),
    COMMA_DELIMITER(","),

    ITEM_DELIMITER("\\[(.*?)]"),
    DATE_DELIMITER("(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2})");

    private final String detail;

    Constant(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return detail;
    }
}
