package store.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import org.junit.jupiter.api.Test;
import store.dto.StockDTO;

class InputHandlerTest {

    @Test
    void 구매할_상품명과_수량_DTO_반환_테스트() {
        String input = "[사이다-2],[감자칩-1]";

        List<StockDTO> items = InputHandler.splitItems(input);

        assertThat(items).extracting("name", "quantity")
                .contains(tuple("사이다", 2),
                          tuple("감자칩", 1));
    }

    @Test
    void 구매할_수량이_양수가_아닌_경우_예외_테스트() {
        String input = "[사이다-1],[감자칩--1]";
        assertThatThrownBy(() -> InputHandler.splitItems(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 수량은 음수가 아닌 정수여야 합니다.");
    }

}