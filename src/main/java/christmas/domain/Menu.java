package christmas.domain;

import static christmas.domain.MenuCategory.APPETIZER;
import static christmas.domain.MenuCategory.DESSERT;
import static christmas.domain.MenuCategory.DRINK;
import static christmas.domain.MenuCategory.MAIN;

import christmas.error.IllegalMenuException;
import java.util.Arrays;

public enum Menu {

    SOUP("양송이수프", APPETIZER, 6000),
    TAPAS("타파스", APPETIZER, 5500),
    SALAD("시저샐러드", APPETIZER, 8000),
    STEAK("티본스테이크", MAIN, 55000),
    RIB("바비큐립", MAIN, 54000),
    SEAFOOD_PASTA("해산물파스타", MAIN, 35000),
    XMAS_PASTA("크리스마스파스타", MAIN, 25000),
    CAKE("초코케이크", DESSERT, 15000),
    ICE_CREAM("아이스크림", DESSERT, 5000),
    COLA("제로콜라", DRINK, 3000),
    RED_WINE("레드와인", DRINK, 60000),
    CHAMPAGNE("샴페인", DRINK, 25000);

    private static final int SINGLE_COUNT = 1;

    private final String name;

    private final MenuCategory category;

    private final int amount;

    Menu(final String name, final MenuCategory category, final int amount) {
        this.name = name;
        this.category = category;
        this.amount = amount;
    }

    public static Menu fromName(final String name) {
        return Arrays.stream(values())
                .filter(menu -> menu.name.equals(name))
                .findFirst()
                .orElseThrow(IllegalMenuException::new);
    }

    public String getName() {
        return name;
    }

    public boolean compareCategory(final MenuCategory category) {
        return this.category.equals(category);
    }

    public Money getMoney(final int count) {
        return new Money(amount * count);
    }

    public Money getMoney() {
        return getMoney(SINGLE_COUNT);
    }
}
