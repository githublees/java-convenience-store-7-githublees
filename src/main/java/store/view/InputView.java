package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;
import store.contant.Constant;
import store.dto.BuyDTO;
import store.exception.CustomException;
import store.exception.ErrorMessage;

public class InputView {
    public static List<BuyDTO> readItem() {
        while (true) {
            try {
                System.out.println(InputMessage.INPUT_PURCHASE_PRODUCT_MESSAGE);
                return InputHandler.splitItems(Console.readLine());
            } catch (CustomException e) {
                System.out.println(e.getErrorMessage());
            }
        }
    }

    public static String readMembership() {
        while (true) {
            try {
                System.out.println(InputMessage.INPUT_MEMBERSHIP_MESSAGE);
                String input = Console.readLine();
                validate(input);
                return input;
            } catch (CustomException e) {
                System.out.println(e.getErrorMessage());
            }
        }
    }

    public static String readPromotion(String name, int quantity) {
        while (true) {
            try {
                System.out.printf(InputMessage.INPUT_PROMOTION_MESSAGE.format(name, quantity));
                String input = Console.readLine();
                validate(input);
                return input;
            } catch (CustomException e) {
                System.out.println(e.getErrorMessage());
            }
        }
    }

    public static String readGetOneFree(String name, int quantity) {
        while (true) {
            try {
                System.out.printf(InputMessage.INPUT_GET_ONE_FREE_MESSAGE.format(name, quantity));
                String input = Console.readLine();
                validate(input);
                return input;
            } catch (CustomException e) {
                System.out.println(e.getErrorMessage());
            }
        }
    }

    public static String readRePurchase() {
        while (true) {
            try {
                System.out.println(InputMessage.INPUT_RE_PURCHASE_MESSAGE);
                String input = Console.readLine();
                validate(input);
                return input;
            } catch (CustomException e) {
                System.out.println(e.getErrorMessage());
            }
        }
    }

    private static void validate(String input) {
        if (isCorrectInput(input)) {
            throw new CustomException(ErrorMessage.INVALID_INPUT_MESSAGE.toString());
        }
    }

    private static boolean isCorrectInput(String input) {
        return !(input.equals(Constant.Y.toString()) || input.equals(Constant.N.toString()));
    }
}
