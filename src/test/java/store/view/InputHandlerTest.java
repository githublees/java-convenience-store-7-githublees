package store.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.dto.BuyDTO;
import store.exception.ErrorMessage;

class InputHandlerTest {

    @Test
    void 구매할_상품명과_수량_DTO_반환_테스트() {
        String input = "[사이다-2],[감자칩-1]";

        List<BuyDTO> items = InputHandler.splitItems(input);

        assertThat(items).extracting("name", "quantity")
                .contains(tuple("사이다", 2),
                          tuple("감자칩", 1));
    }

    @ParameterizedTest
    @ValueSource(strings = {"[사이다-1],[감자칩--1]", "[[콜라-3]],[에너지바-5]", "[사이다-1],[콜라-a]"})
    void 올바른_형식이_아닌_경우_예외_테스트(String input) {
        assertThatThrownBy(() -> InputHandler.splitItems(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NOT_FIT_THE_FORM_MESSAGE.toString());
    }

}