package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.view.InputHandler;

class PromotionTest {
    private Promotions promotions;

    @BeforeEach
    void setUp() {
        List<Promotion> list = new ArrayList<>();
        list.add(new Promotion("탄산2+1", 2, 1, InputHandler.parseDate("2024-01-01"), InputHandler.parseDate("2024-12-31")));
        list.add(new Promotion("MD추천상품", 1, 1,InputHandler.parseDate("2024-01-01"), InputHandler.parseDate("2024-12-31")));

        promotions = new Promotions(list);
    }

    @Test
    void 프로모션_적용_상품_개수_가져오기() {
        Promotion promotion = promotions.findPromotion("탄산2+1");
        assertThat(promotion.getApplyProduct(10)).isEqualTo(6);
    }

    @Test
    void 프로모션_적용_후_증정_상품_개수_가져오기() {
        Promotion promotion = promotions.findPromotion("탄산2+1");
        assertThat(promotion.getFreeProduct(10)).isEqualTo(3);
    }

    @Test
    void 프로모션_기간_적용_유무_테스트() {
        Promotion promotion = promotions.findPromotion("탄산2+1");
        LocalDateTime before = LocalDate.of(2023, 01, 01).atStartOfDay();
        LocalDateTime after = LocalDate.of(2024, 01, 02).atStartOfDay();
        assertThat(promotion.isLastDate(before)).isTrue();
        assertThat(promotion.isLastDate(after)).isFalse();
    }
}