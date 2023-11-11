package christmas.domain.promotion.condition;

import christmas.domain.Order;
import christmas.domain.promotion.PromotionCondition;
import java.time.Month;

public class MonthCondition implements PromotionCondition {

    private final Month month;

    public MonthCondition(final Month month) {
        this.month = month;
    }

    @Override
    public boolean canReceive(final Order order) {
        return order.hasOrderedAt(month);
    }
}
