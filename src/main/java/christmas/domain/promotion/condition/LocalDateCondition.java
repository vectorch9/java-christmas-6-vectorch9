package christmas.domain.promotion.condition;

import christmas.domain.Order;
import christmas.domain.promotion.PromotionCondition;
import java.time.LocalDate;

public class LocalDateCondition implements PromotionCondition {

    private final LocalDate localDate;

    public LocalDateCondition(final LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public boolean canReceive(final Order order) {
        return order.hasOrderedAt(localDate);
    }
}
