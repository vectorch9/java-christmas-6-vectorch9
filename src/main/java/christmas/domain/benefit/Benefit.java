package christmas.domain.benefit;

import christmas.domain.Money;

public abstract class Benefit {

    private final Money promotedMoney;

    private final String name;

    protected Benefit(final Money promotedMoney, final String name) {
        this.promotedMoney = promotedMoney;
        this.name = name;
    }

    public final Money getPromotedMoney() {
        return promotedMoney;
    }

    public String getName() {
        return name;
    }
}
