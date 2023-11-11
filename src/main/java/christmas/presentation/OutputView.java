package christmas.presentation;

import christmas.domain.Bill;
import christmas.domain.Menu;
import christmas.domain.Money;
import christmas.domain.Order;
import christmas.domain.badge.Badge;
import christmas.domain.benefit.Benefit;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

public class OutputView {

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance();
    private static final String NONE = "없음\n";
    private static final String WELCOME_MESSAGE = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String WELCOME_FOOTER = "12월 26일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n";
    private static final String ORDER_HEADER = "<주문 메뉴>";
    private static final String MENU_COUNT_FORMAT = "%s %d개\n";
    private static final String MONEY_FORMAT = "%s원\n";
    private static final String TOTAL_HEADER = "<할인 전 총주문 금액>";
    private static final String GIVEAWAY_HEADER = "<증정 메뉴>";
    private static final String PROMOTION_HEADER = "<혜택 내역>";
    private static final String PROMOTION_FORMAT = "%s: -%s원\n";
    private static final String PROMOTION_MONEY_HEADER = "<총혜택 금액>";
    private static final String PROMOTED_TOTAL_HEADER = "<할인 후 예상 결제 금액>";
    private static final String BADGE_HEADER = "<12월 이벤트 배지>";

    public OutputView() {
    }

    public void printWelcome() {
        System.out.println(WELCOME_MESSAGE);
    }

    public void printWelcomeFooter() {
        System.out.println(WELCOME_FOOTER);
    }

    public void printOrder(final Order order) {
        printMenus(ORDER_HEADER, order.getMenus());
    }

    public void printBill(final Bill bill) {
        printMoney(TOTAL_HEADER, bill.getTotal());
        printMenus(GIVEAWAY_HEADER, bill.getGiveawayMenus());
        printBenefits(bill.getBenefits());
        printMoney(PROMOTION_MONEY_HEADER, bill.getPromoted());
        printMoney(PROMOTED_TOTAL_HEADER, bill.getPromotedTotal());
    }

    private void printMoney(final String header, final Money money) {
        System.out.println(header);
        System.out.printf(MONEY_FORMAT, NUMBER_FORMAT.format(money.getAmount()));
        System.out.println();
    }

    private void printMenus(final String header, final Map<Menu, Integer> menus) {
        System.out.println(header);
        if (menus.isEmpty()) {
            System.out.println(NONE);
            return;
        }
        menus.forEach((menu, count) -> System.out.printf(MENU_COUNT_FORMAT, menu.getName(), count));
        System.out.println();
    }

    private void printBenefits(final List<Benefit> benefits) {
        System.out.println(PROMOTION_HEADER);
        if (benefits.isEmpty()) {
            System.out.println(NONE);
            return;
        }
        benefits.forEach(benefit -> System.out.printf(PROMOTION_FORMAT,
                benefit.getName(), NUMBER_FORMAT.format((benefit.getPromotedMoney().getAmount()))));
        System.out.println();
    }

    public void printBadge(final Badge badge) {
        System.out.println(BADGE_HEADER);
        System.out.println(badge.getName());
    }
}
