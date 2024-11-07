package store.view;

import java.text.NumberFormat;
import store.domain.Item;
import store.domain.Products;

public class OutputView {

    public void printProducts(Products products) {
        NumberFormat format = NumberFormat.getNumberInstance();

        System.out.println("안녕하세요 W편의점입니다.\n현재 보유하고 있는 상품입니다.\n");
        for (Item item : products.getProducts()) {
            String name = item.getName();
            String price = format.format(item.getPrice()) + "원";
            String quantity = format.format(item.getStock()) + "개";
            if (item.getStock() == 0) {
                quantity = "재고 없음";
            }
            String promotion = "";
            if (item.getPromotion() != null) {
                promotion = item.getPromotion().getName();
            }

            System.out.println(name + " " + price + " " + quantity + " " + promotion);
        }
        System.out.println();
    }
}
