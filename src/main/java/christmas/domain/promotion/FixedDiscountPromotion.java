package christmas.domain.promotion;

import christmas.domain.Money;
import christmas.domain.Order;
import christmas.domain.benefit.Benefit;
import christmas.domain.benefit.DiscountBenefit;

public class FixedDiscountPromotion extends Promotion {

    private final Money money;

    public FixedDiscountPromotion(final PromotionCondition promotionCondition, final String name,
            final Money money) {
        super(promotionCondition, name);
        this.money = money;
    }

    @Override
    protected Benefit calculate(final Order order, final String name) {
        return new DiscountBenefit(money, name);
    }
}
