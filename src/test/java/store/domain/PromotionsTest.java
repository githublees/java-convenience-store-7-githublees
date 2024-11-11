package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.view.InputHandler;

class PromotionsTest {
    private Promotions promotions;

    @BeforeEach
    void setUp() {
        List<Promotion> list = new ArrayList<>();
        list.add(new Promotion("탄산2+1", 2, 1, InputHandler.parseDate("2024-01-01"), InputHandler.parseDate("2024-12-31")));
        list.add(new Promotion("MD추천상품", 1, 1,InputHandler.parseDate("2024-01-01"), InputHandler.parseDate("2024-12-31")));

        promotions = new Promotions(list);
    }

    @Test
    void 이름_기준_프로모션_가져오기() {
        assertThat(promotions.findPromotion("탄산2+1"))
                .extracting("name", "buy", "get", "startDate", "endDate")
                .contains("탄산2+1", 2, 1, InputHandler.parseDate("2024-01-01"), InputHandler.parseDate("2024-12-31"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"탄산1+1", "반짝할인"})
    void 존재하지_않는_프로모션_예외_처리(String input) {
        assertThat(promotions.findPromotion(input)).isEqualTo(null);
    }
}