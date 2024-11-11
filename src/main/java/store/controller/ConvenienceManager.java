package store.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import store.contant.Constant;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.dto.ProductsDTO;
import store.dto.ReceiptDTO;
import store.exception.CustomException;
import store.exception.ErrorMessage;
import store.view.InputHandler;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceManager {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String PRODUCTS_FILE_PATH = Constant.PRODUCTS_FILE_PATH.toString();
    private static final String PROMOTIONS_FILE_PATH = Constant.PROMOTIONS_FILE_PATH.toString();

    private Promotions promotions;
    private Products products;

    public ConvenienceManager() {
        try {
            this.promotions = new Promotions(initPromotions());
            this.products = new Products(initProducts(this.promotions));
        } catch (IOException e) {
            logger.info(ErrorMessage.FILE_IOEXCEPTION_MESSAGE::toString);
        }
    }

    public void run() {
        try {
            do {
                OutputView.printProducts(products);
                List<ProductsDTO> sameItems = products.getSameProducts();
                ReceiptDTO receiptDTO = products.buyProduct(sameItems);
                membership(receiptDTO);
            } while (hasRePurchase());
        } catch (CustomException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    private void membership(ReceiptDTO receiptDTO) {
        if (hasApplyMembership()) {
            OutputView.printReceipt(receiptDTO, true);
            return;
        }
        OutputView.printReceipt(receiptDTO, false);
    }

    private List<Promotion> initPromotions() throws IOException {
        List<String> strings = Files.readAllLines(Paths.get(PROMOTIONS_FILE_PATH));
        return strings.stream()
                .skip(1)
                .map(this::makePromotion)
                .toList();
    }

    private List<Product> initProducts(Promotions promotions) throws IOException {
        List<String> strings = Files.readAllLines(Paths.get(PRODUCTS_FILE_PATH));
        return strings.stream()
                .skip(1)
                .map(line -> makeProduct(promotions, line))
                .toList();
    }

    private Promotion makePromotion(String line) {
        final String[] promotion = split(line);
        final String name = promotion[0];
        final int buy = Integer.parseInt(promotion[1]);
        final int get = Integer.parseInt(promotion[2]);
        final LocalDateTime startDate = InputHandler.parseDate(promotion[3]);
        final LocalDateTime endDate = InputHandler.parseDate(promotion[4]);
        return new Promotion(name, buy, get, startDate, endDate);
    }

    private Product makeProduct(Promotions promotions, String line) {
        final String[] product = split(line);
        final String name = product[0];
        final int price = Integer.parseInt(product[1]);
        final int stock = Integer.parseInt(product[2]);
        final Promotion promotion = promotions.findPromotion(product[3]);
        return new Product(name, price, stock, promotion);
    }

    private static String[] split(String line) {
        return line.split(Constant.COMMA_DELIMITER.toString());
    }

    private boolean hasRePurchase() {
        return InputView.readRePurchase().equals(Constant.Y.toString());
    }

    private boolean hasApplyMembership() {
        return InputView.readMembership().equals(Constant.Y.toString());
    }
}
