package store.domain;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.Application;
import store.dto.BuyDTO;
import store.dto.ProductsDTO;
import store.dto.ReceiptDTO;
import store.exception.ErrorMessage;
import store.view.InputHandler;

class ProductsTest {

    private Products products;
    private Promotions promotions;

    @BeforeEach
    void setUp() {
        List<Promotion> list = new ArrayList<>();
        list.add(new Promotion("탄산2+1", 2, 1, InputHandler.parseDate("2024-01-01"),InputHandler.parseDate("2024-12-31")));

        promotions = new Promotions(list);

        List<Product> items = new ArrayList<>();
        items.add(new Product("콜라", 1000, 10, promotions.findPromotion("탄산2+1")));
        items.add(new Product("콜라", 1000, 10, null));

        products = new Products(items);
    }

    @Test
    void 프로모션_상품과_아닌_상품_가져오기() {
        BuyDTO buyDTO = new BuyDTO("콜라", 3);
        List<BuyDTO> shopping = List.of(buyDTO);

        List<ProductsDTO> finds = products.changeProductsDTO(shopping);

        assertThat(finds.getFirst().with()).extracting("name", "price", "stock", "promotion")
                .contains("콜라", 1000, 10, promotions.findPromotion("탄산2+1"));
        assertThat(finds.getFirst().without()).extracting("name", "price", "stock", "promotion")
                .contains("콜라", 1000, 10, null);
    }

    @Test
    void 상품_구매하기() {
        BuyDTO buyDTO = new BuyDTO("콜라", 3);
        List<BuyDTO> shopping = List.of(buyDTO);
        List<ProductsDTO> finds = products.changeProductsDTO(shopping);

        ReceiptDTO receiptDTO = products.buyProduct(finds);

        assertThat(receiptDTO.cart().getFirst().getTotalAmount()).isEqualTo(3000);
        assertThat(receiptDTO.free().getFirst().getTotalAmount()).isEqualTo(1000);
    }

    @Test
    void 존재하지_않는_상품_구매_예외_테스트() {
        BuyDTO buyDTO = new BuyDTO("에너지바", 5);
        List<BuyDTO> shopping = List.of(buyDTO);
        assertThatThrownBy(() -> products.changeProductsDTO(shopping))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NOT_EXIST_PRODUCT_MESSAGE.toString());
    }
}