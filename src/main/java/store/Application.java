package store;

import store.controller.ConvenienceManager;
import store.domain.Products;
import store.domain.Promotions;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        ConvenienceManager convenienceManager = new ConvenienceManager(
                new Products(), new Promotions(), new InputView(), new OutputView());
        convenienceManager.run();
    }
}
