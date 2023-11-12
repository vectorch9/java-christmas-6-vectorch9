package christmas.presentation;

import camp.nextstep.edu.missionutils.Console;
import christmas.error.IllegalMenuException;
import christmas.error.IllegalVisitedDayException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InputView {

    private static final int YEAR = 2023;
    private static final int MONTH = 12;
    private static final String ORDER_DELIMITER = ",";
    private static final String MENU_COUNT_DELIMITER = "-";
    private static final String DATE_HEADER = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    private static final String MENU_HEADER = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";
    private static final String MENU_FOOTER = "12월 26일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n";


    public InputView() {
    }

    public LocalDate readVisitedDay() {
        try {
            System.out.println(DATE_HEADER);
            final String input = Console.readLine().trim();
            final int day = Integer.parseInt(input);
            return LocalDate.of(YEAR, MONTH, day);
        } catch (final RuntimeException e) {
            throw new IllegalVisitedDayException();
        }
    }

    public Map<String, Integer> readMenus() {
        try {
            System.out.println(MENU_HEADER);
            final String input = Console.readLine().trim();
            final Map<String, Integer> result = new HashMap<>();
            final String[] orders = input.split(ORDER_DELIMITER);
            Arrays.stream(orders).forEach(order -> addToResult(order, result));
            System.out.println(MENU_FOOTER);
            return result;
        } catch (final RuntimeException e) {
            throw new IllegalMenuException();
        }
    }

    private void addToResult(final String order, final Map<String, Integer> result) {
        final String[] menuAndCount = order.split(MENU_COUNT_DELIMITER);
        if (result.containsKey(menuAndCount[0])) {
            throw new IllegalMenuException();
        }
        result.put(menuAndCount[0], Integer.parseInt(menuAndCount[1]));
    }
}
