package christmas.domain;

import static christmas.support.FixDiscountPromotionFixtures.항상_적용되는_1000원_할인;
import static christmas.support.FixDiscountPromotionFixtures.항상_적용되지_않는_1000원_할인;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.entry;
import static org.junit.jupiter.api.Assertions.assertAll;

import christmas.domain.promotion.Promotion;
import christmas.error.IllegalMenuException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ShopTest {

    @DisplayName("주문을 한다.")
    @Test
    void order() {
        // given
        final List<Promotion> promotions = List.of(항상_적용되는_1000원_할인.생성(), 항상_적용되는_1000원_할인.생성(),
                항상_적용되지_않는_1000원_할인.생성());
        final Shop shop = new Shop(promotions);
        final Map<String, Integer> menuStrings = Map.of("시저샐러드", 2, "타파스", 1);
        final LocalDate visitedAt = LocalDate.of(2023, 12, 1);

        // when
        final Order order = shop.order(visitedAt, menuStrings);

        // then
        assertThat(order.getMenus()).containsOnly(entry(Menu.SALAD, 2), entry(Menu.TAPAS, 1));
    }

    @DisplayName("없는 메뉴를 주문하면 예외를 던진다.")
    @Test
    void orderIllegalMenu() {
        // given
        final List<Promotion> promotions = List.of(항상_적용되는_1000원_할인.생성(), 항상_적용되는_1000원_할인.생성(),
                항상_적용되지_않는_1000원_할인.생성());
        final Shop shop = new Shop(promotions);
        final Map<String, Integer> menuStrings = Map.of("파인애플피자", 2, "민트초코", 1);
        final LocalDate visitedAt = LocalDate.of(2023, 12, 1);

        // when & then
        assertThatThrownBy(() -> shop.order(visitedAt, menuStrings))
                .isInstanceOf(IllegalMenuException.class);
    }

    @DisplayName("영수증을 받는다.")
    @Test
    void takeBill() {
        // given
        final List<Promotion> promotions = List.of(항상_적용되는_1000원_할인.생성(), 항상_적용되는_1000원_할인.생성(),
                항상_적용되지_않는_1000원_할인.생성());
        final Shop shop = new Shop(promotions);

        final Map<Menu, Integer> menus = Map.of(Menu.SALAD, 2, Menu.TAPAS, 1);
        final LocalDate visitedAt = LocalDate.of(2023, 12, 1);
        final Order order = new Order(menus, visitedAt);

        // when
        final Bill bill = shop.takeBill(order);

        // then
        assertAll(() -> {
            assertThat(bill.getTotal()).isEqualTo(order.getTotal());
            assertThat(bill.getPromoted()).isEqualTo(new Money(2000));
        });
    }
}
