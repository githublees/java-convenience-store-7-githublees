package store.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import store.domain.Item;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.dto.StockDTO;
import store.view.InputHandler;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceManager {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String PRODUCTS_FILE_PATH = "./src/main/resources/products.md";
    private static final String PROMOTIONS_FILE_PATH = "./src/main/resources/promotions.md";

    private final Products products;
    private final Promotions promotions;
    private final InputView inputView;
    private final OutputView outputView;

    public ConvenienceManager(Products products, Promotions promotions, InputView inputView, OutputView outputView) {
        this.products = products;
        this.promotions = promotions;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        try {
            initPromotions();
            initProducts();
        } catch (IOException e) {
            logger.info(() -> "[ERROR] 파일 입출력 에러입니다.");
        }

        do {
            outputView.printProducts(products);
            List<StockDTO> cart = InputHandler.splitItems(inputView.readItem());

        } while (inputView.readRePurchase().equals("Y"));
    }

    private void initPromotions() throws IOException {
        List<String> strings = Files.readAllLines(Paths.get(PROMOTIONS_FILE_PATH));

        strings.stream()
                .skip(1)
                .map(line -> {
                    final String[] promotion = line.split(",");
                    final String name = promotion[0];
                    final int buy = Integer.parseInt(promotion[1]);
                    final int get = Integer.parseInt(promotion[2]);
                    final String startDate = promotion[3];
                    final String endDate = promotion[4];
                    return new Promotion(name, buy, get, startDate, endDate);
                })
                .forEach(promotions::addPromotion);
    }

    private void initProducts() throws IOException {
        List<String> strings = Files.readAllLines(Paths.get(PRODUCTS_FILE_PATH));

        strings.stream()
                .skip(1)
                .map(line -> {
                    final String[] product = line.split(",");
                    final String name = product[0];
                    final int price = Integer.parseInt(product[1]);
                    final int stock = Integer.parseInt(product[2]);
                    final Promotion promotion = promotions.getPromotion(product[3]);
                    return new Item(name, price, stock, promotion);
                })
                .forEach(products::addItem);
    }
}
