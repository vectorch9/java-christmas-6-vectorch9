package christmas.support;

import static christmas.domain.Menu.CHAMPAGNE;
import static christmas.domain.Menu.SALAD;
import static christmas.domain.Menu.STEAK;
import static christmas.domain.Menu.TAPAS;

import christmas.domain.Menu;
import christmas.domain.Order;
import java.time.LocalDate;
import java.util.Map;

public enum OrderFixtures {

    타파스_1개_샐러드_2개(Map.of(TAPAS, 1, SALAD, 2), LocalDate.of(2023,12,25)),
    스테이크_3개_샴페인_1병(Map.of(STEAK, 3, CHAMPAGNE, 1), LocalDate.of(2023,12,10));

    public final Map<Menu, Integer> menus;

    public final LocalDate visitedAt;

    OrderFixtures(final Map<Menu, Integer> menus, final LocalDate visitedAt) {
        this.menus = menus;
        this.visitedAt = visitedAt;
    }

    public Order 생성() {
        return new Order(menus, visitedAt);
    }
}
