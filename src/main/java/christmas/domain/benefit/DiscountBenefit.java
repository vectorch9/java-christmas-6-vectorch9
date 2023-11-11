package christmas.domain.benefit;

import christmas.domain.Money;

public class DiscountBenefit extends Benefit {

    public DiscountBenefit(final Money promotedMoney, final String name) {
        super(promotedMoney, name);
    }
}
