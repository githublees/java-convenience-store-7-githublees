package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.dto.StockDTO;
import store.exception.ErrorMessage;
import store.view.InputHandler;

class ProductTest {
    private Promotions promotions;
    private Product product;

    @BeforeEach
    void setUp() {
        List<Promotion> list = new ArrayList<>();
        list.add(new Promotion("탄산2+1", 2, 1, InputHandler.parseDate("2024-01-01"), InputHandler.parseDate("2024-12-31")));
        list.add(new Promotion("MD추천상품", 1, 1,InputHandler.parseDate("2024-01-01"), InputHandler.parseDate("2024-12-31")));

        promotions = new Promotions(list);

        product = new Product("콜라", 1000, 10, promotions.findPromotion("탄산2+1"));
    }

    @Test
    void 프로모션_적용되지_않는_개수_반환() {
        StockDTO purchaseState = product.getPurchaseState(10);
        assertThat(purchaseState.remainStock()).isEqualTo(1);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 4, 11})
    void 프로모션_적용_후_더_구매해야할_상품_개수_반환(int number) {
        int stock = product.getStock();
        int remain = product.applyClearanceStock(number);

        if (stock < number) {
            assertThat(remain).isEqualTo(number - stock);
            return;
        }
        assertThat(remain).isEqualTo(0);
    }
    
    @Test
    void 프로모션_미적용_재고정리() {
        product.notApplyClearanceStock(5);
        assertThat(product.getStock()).isEqualTo(5);
    }
    
    @Test
    void 구매_수량이_재고보다_많은_경우_예외_테스트() {
        assertThatThrownBy(() -> product.notApplyClearanceStock(11))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.QUANTITY_OVER_MESSAGE.toString());
    }

}