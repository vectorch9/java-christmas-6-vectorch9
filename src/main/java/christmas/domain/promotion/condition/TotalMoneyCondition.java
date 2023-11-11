package christmas.domain.promotion.condition;

import christmas.domain.Money;
import christmas.domain.Order;
import christmas.domain.promotion.PromotionCondition;

public class TotalMoneyCondition implements PromotionCondition {

    private final Money threshold;

    public TotalMoneyCondition(final Money threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean canReceive(final Order order) {
        return order.getTotal().isGreaterThan(threshold);
    }
}
