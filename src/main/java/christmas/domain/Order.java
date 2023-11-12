package christmas.domain;

import static christmas.domain.MenuCategory.DRINK;

import christmas.error.IllegalMenuException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Order {

    private static final int MAX_MENU_COUNT = 20;

    private final Map<Menu, Integer> menus;

    private final LocalDate orderedAt;

    public Order(final Map<Menu, Integer> menus, final LocalDate orderedAt) {
        validateNoOnlyDrink(menus);
        validateMenuCount(menus);
        this.menus = menus;
        this.orderedAt = orderedAt;
    }

    private void validateMenuCount(final Map<Menu, Integer> menus) {
        if (menus.values().stream().anyMatch(count -> count <= 0)) {
            throw new IllegalMenuException();
        }
        if (menus.isEmpty() || menus.values().stream().mapToInt(Integer::intValue).sum() >= MAX_MENU_COUNT) {
            throw new IllegalMenuException();
        }
    }

    private void validateNoOnlyDrink(final Map<Menu, Integer> menus) {
        if (menus.keySet().stream().allMatch(key -> key.compareCategory(DRINK))) {
            throw new IllegalMenuException();
        }
    }

    public Map<Menu, Integer> getMenus() {
        return Collections.unmodifiableMap(menus);
    }

    public Money getTotal() {
        final List<Money> moneys = menus.entrySet().stream()
                .map(entry -> entry.getKey().getMoney(entry.getValue())).toList();
        return moneys.stream().reduce(Money.ZERO, Money::add);
    }

    public int getDaysAfterOrderedAt(final LocalDate origin) {
        return Long.valueOf(ChronoUnit.DAYS.between(origin, orderedAt)).intValue();
    }

    public boolean hasOrderedAt(final Month month) {
        return orderedAt.getMonth().equals(month);
    }

    public boolean hasOrderedAt(final DayOfWeek dayOfWeek) {
        return orderedAt.getDayOfWeek().equals(dayOfWeek);
    }

    public boolean hasOrderedAt(final LocalDate localDate) {
        return orderedAt.isEqual(localDate);
    }

    public boolean hasOrderedBetween(final LocalDate start, final LocalDate end) {
        return orderedAt.isEqual(start) || orderedAt.isEqual(end) ||
                (orderedAt.isAfter(start) && orderedAt.isBefore(end));
    }

    public int countByCategory(final MenuCategory category) {
        return menus.entrySet().stream().mapToInt(e -> getCountAfterComparingCategory(category, e)).sum();
    }

    private int getCountAfterComparingCategory(final MenuCategory category, final Entry<Menu, Integer> e) {
        if (e.getKey().compareCategory(category)) {
            return e.getValue();
        }
        return 0;
    }
}

