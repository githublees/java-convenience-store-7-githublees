package store.view;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.dto.StockDTO;

public class InputHandler {
    public static List<StockDTO> splitItems(String input) {
        Matcher matcher = Pattern.compile("\\[(.*?)]").matcher(input);
        return matcher.results()
                .map(find -> {
                    String[] item = split(find.group(1));
                    return new StockDTO(item[0], toInt(item[1]));
                }).toList();
    }

    public static int toInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 수량은 음수가 아닌 정수여야 합니다.");
        }
    }

    public static String[] split(String input) {
        return input.split("-");
    }
}
