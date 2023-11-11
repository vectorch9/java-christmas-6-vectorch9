package christmas.domain;

import christmas.domain.benefit.Benefit;
import christmas.domain.promotion.Promotion;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Shop {

    private final List<Promotion> promotions;

    public Shop(final List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public Order order(final LocalDate orderedAt, final Map<String, Integer> orderStrings) {
        final Map<Menu, Integer> menus = new EnumMap<>(Menu.class);
        orderStrings.forEach((menuString, count) -> menus.put(Menu.fromName(menuString), count));
        return new Order(menus, orderedAt);
    }

    public Bill takeBill(final Order order) {
        final List<Benefit> benefits = new ArrayList<>();
        promotions.forEach(promotion -> promotion.take(order).ifPresent(benefits::add));
        return new Bill(order.getTotal(), benefits);
    }
}
