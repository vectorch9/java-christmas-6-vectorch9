package christmas.domain;

import static christmas.domain.Menu.CHAMPAGNE;
import static christmas.domain.Menu.COLA;
import static christmas.domain.Menu.SEAFOOD_PASTA;
import static christmas.domain.Menu.STEAK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.error.IllegalMenuException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderTest {

    @DisplayName("음료만 주문할 순 없다.")
    @Test
    void cantOrderOnlyDrink() {
        // given
        final Map<Menu, Integer> menus = Map.of(COLA, 2, CHAMPAGNE, 1);
        final LocalDate visitedAt = LocalDate.of(2023, 12, 1);

        // when & then
        assertThatThrownBy(() -> new Order(menus, visitedAt)).isInstanceOf(IllegalMenuException.class);
    }

    @DisplayName("0개의 메뉴를 주문할 순 없다.")
    @Test
    void cantOrderZeroCount() {
        // given
        final Map<Menu, Integer> menus = Map.of(STEAK, 0);
        final LocalDate visitedAt = LocalDate.of(2023, 12, 1);

        // when & then
        assertThatThrownBy(() -> new Order(menus, visitedAt)).isInstanceOf(IllegalMenuException.class);
    }

    @DisplayName("총 0개의 메뉴를 주문할 순 없다.")
    @Test
    void cantOrderEmptyMenu() {
        // given
        final Map<Menu, Integer> menus = Map.of();
        final LocalDate visitedAt = LocalDate.of(2023, 12, 1);

        // when & then
        assertThatThrownBy(() -> new Order(menus, visitedAt)).isInstanceOf(IllegalMenuException.class);
    }

    @DisplayName("메뉴의 총 합은 20개를 넘길 수 없다.")
    @Test
    void cantOrderGreaterThan20() {
        // given
        final Map<Menu, Integer> menus = Map.of(STEAK, 15, CHAMPAGNE, 6);
        final LocalDate visitedAt = LocalDate.of(2023, 12, 1);

        // when & then
        assertThatThrownBy(() -> new Order(menus, visitedAt)).isInstanceOf(IllegalMenuException.class);
    }

    @DisplayName("주문의 총액을 구한다.")
    @Test
    void getTotal() {
        // given
        final Map<Menu, Integer> menus = Map.of(STEAK, 15, CHAMPAGNE, 4);
        final LocalDate visitedAt = LocalDate.of(2023, 12, 1);
        final Money expected = STEAK.getMoney(15).add(CHAMPAGNE.getMoney(4));
        final Order order = new Order(menus, visitedAt);

        // when
        final Money actual = order.getTotal();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("기준으로부터 몇일이 지났는지 구한다.")
    @Test
    void getDaysAfterOrderedAt() {
        // given
        final Map<Menu, Integer> menus = Map.of(STEAK, 15, CHAMPAGNE, 4);
        final LocalDate visitedAt = LocalDate.of(2023, 12, 1);
        final Order order = new Order(menus, visitedAt);
        final int expected = 2;
        final LocalDate origin = visitedAt.minusDays(expected);

        // when
        final int actual = order.getDaysAfterOrderedAt(origin);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("특정 월에 주문했다면 true를 반환한다.")
    @Test
    void hasOrderedAtMonth() {
        // given
        final Map<Menu, Integer> menus = Map.of(STEAK, 15, CHAMPAGNE, 4);
        final LocalDate visitedAt = LocalDate.of(2023, 12, 1);
        final Order order = new Order(menus, visitedAt);
        final Month month = Month.DECEMBER;

        // when
        final boolean actual = order.hasOrderedAt(month);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("특정 월에 주문하지 않았다면 false를 반환한다.")
    @Test
    void hasNotOrderedAtMonth() {
        // given
        final Map<Menu, Integer> menus = Map.of(STEAK, 15, CHAMPAGNE, 4);
        final LocalDate visitedAt = LocalDate.of(2023, 12, 1);
        final Order order = new Order(menus, visitedAt);
        final Month month = Month.NOVEMBER;

        // when
        final boolean actual = order.hasOrderedAt(month);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("특정 요일에 주문했다면 true를 반환한다.")
    @Test
    void hasOrderedAtDayOfWeek() {
        // given
        final Map<Menu, Integer> menus = Map.of(STEAK, 15, CHAMPAGNE, 4);
        final LocalDate visitedAt = LocalDate.of(2023, 12, 1);
        final Order order = new Order(menus, visitedAt);
        final DayOfWeek dayOfWeek = DayOfWeek.FRIDAY;

        // when
        final boolean actual = order.hasOrderedAt(dayOfWeek);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("특정 월에 주문하지 않았다면 false를 반환한다.")
    @Test
    void hasNotOrderedAtDayOfWeek() {
        // given
        final Map<Menu, Integer> menus = Map.of(STEAK, 15, CHAMPAGNE, 4);
        final LocalDate visitedAt = LocalDate.of(2023, 12, 1);
        final Order order = new Order(menus, visitedAt);
        final DayOfWeek dayOfWeek = DayOfWeek.TUESDAY;

        // when
        final boolean actual = order.hasOrderedAt(dayOfWeek);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("특정 날짜에 주문했다면 true를 반환한다.")
    @Test
    void hasOrderedAtDate() {
        // given
        final Map<Menu, Integer> menus = Map.of(STEAK, 15, CHAMPAGNE, 4);
        final LocalDate visitedAt = LocalDate.of(2023, 12, 1);
        final Order order = new Order(menus, visitedAt);

        // when
        final boolean actual = order.hasOrderedAt(visitedAt);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("특정 날짜에 주문하지 않았다면 false를 반환한다.")
    @Test
    void hasNotOrderedAtDate() {
        // given
        final Map<Menu, Integer> menus = Map.of(STEAK, 15, CHAMPAGNE, 4);
        final LocalDate visitedAt = LocalDate.of(2023, 12, 1);
        final Order order = new Order(menus, visitedAt);

        // when
        final boolean actual = order.hasOrderedAt(visitedAt.minusDays(1));

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("특정 날짜 간격에 주문했다면 true를 반환한다.")
    @Test
    void hasOrderedBetween() {
        // given
        final Map<Menu, Integer> menus = Map.of(STEAK, 15, CHAMPAGNE, 4);
        final LocalDate visitedAt = LocalDate.of(2023, 12, 1);
        final Order order = new Order(menus, visitedAt);
        final LocalDate start = LocalDate.of(2023, 11, 30);
        final LocalDate end = LocalDate.of(2023, 12, 2);

        // when
        final boolean actual = order.hasOrderedBetween(start, end);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("특정 날짜 간격에 주문하지 않았다면 false를 반환한다.")
    @Test
    void hasNotOrdererBetween() {
        // given
        final Map<Menu, Integer> menus = Map.of(STEAK, 15, CHAMPAGNE, 4);
        final LocalDate visitedAt = LocalDate.of(2023, 12, 1);
        final Order order = new Order(menus, visitedAt);
        final LocalDate start = LocalDate.of(2023, 12, 2);
        final LocalDate end = LocalDate.of(2023, 12, 3);

        // when
        final boolean actual = order.hasOrderedBetween(start, end);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("특정 메뉴 종류의 개수를 구한다.")
    @Test
    void getMenuCategoryCount() {
        // given
        final Map<Menu, Integer> menus = Map.of(STEAK, 15, SEAFOOD_PASTA, 4);
        final LocalDate visitedAt = LocalDate.of(2023, 12, 1);
        final Order order = new Order(menus, visitedAt);

        // when
        final int actual = order.countByCategory(MenuCategory.MAIN);

        // then
        assertThat(actual).isEqualTo(19);
    }
}
