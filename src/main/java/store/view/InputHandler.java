package store.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.contant.Constant;
import store.dto.BuyDTO;
import store.exception.CustomException;
import store.exception.ErrorMessage;

public class InputHandler {
    private static final Pattern patternItem = Pattern.compile(Constant.ITEM_DELIMITER.toString());
    private static final Pattern patternDate = Pattern.compile(Constant.DATE_DELIMITER.toString());

    public static List<BuyDTO> splitItems(String input) {
        Matcher matcher = patternItem.matcher(input);
        return matcher.results()
                .map(find -> {
                    String content = find.group(1);
                    validate(content);
                    String[] item = split(content);
                    return new BuyDTO(item[0], toInt(item[1]));
                })
                .toList();
    }

    private static void validate(String input) {
        if (input.contains("[") || input.contains("]")) {
            throw new CustomException(ErrorMessage.NOT_FIT_THE_FORM_MESSAGE.toString());
        }
    }

    public static LocalDateTime parseDate(String input) {
        Matcher matcher = patternDate.matcher(input);
        if (matcher.matches()) {
            int year = Integer.parseInt(matcher.group(Constant.YEAR.toString()));
            int month = Integer.parseInt(matcher.group(Constant.MONTH.toString()));
            int day = Integer.parseInt(matcher.group(Constant.DAY.toString()));
            return LocalDate.of(year, month, day).atStartOfDay();
        }
        throw new CustomException(ErrorMessage.FILE_IOEXCEPTION_MESSAGE.toString());
    }

    public static int toInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new CustomException(ErrorMessage.NOT_FIT_THE_FORM_MESSAGE.toString());
        }
    }

    private static String[] split(String input) {
        return input.split(Constant.DEFAULT_DELIMITER.toString());
    }
}
