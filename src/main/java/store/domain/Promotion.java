package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;

public class Promotion {
    private final String name;
    private final int buy;
    private final int get;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public Promotion(String name, int buy, int get, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getApplyProduct(int quantity) {
        return (quantity / (buy + get)) * buy;
    }

    public int getFreeProduct(int quantity) {
        return quantity / (buy + get) * get;
    }

    public boolean isLastDate(LocalDateTime nowTime) {
        return nowTime.isBefore(startDate) || nowTime.isAfter(endDate);
    }

    public String getName() {
        return name;
    }

    public int getApplyPromotion() {
        return buy + get;
    }
}
