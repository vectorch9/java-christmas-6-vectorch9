package christmas.domain.promotion.condition;

import christmas.domain.Order;
import christmas.domain.promotion.PromotionCondition;
import java.time.LocalDate;

public class PeriodCondition implements PromotionCondition {

    private final LocalDate start;

    private final LocalDate end;

    public PeriodCondition(final LocalDate start, final LocalDate end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean canReceive(final Order order) {
        return order.hasOrderedBetween(start, end);
    }
}
