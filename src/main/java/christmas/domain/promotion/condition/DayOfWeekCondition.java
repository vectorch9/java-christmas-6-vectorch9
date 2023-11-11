package christmas.domain.promotion.condition;

import christmas.domain.Order;
import christmas.domain.promotion.PromotionCondition;
import java.time.DayOfWeek;
import java.util.List;

public class DayOfWeekCondition implements PromotionCondition {

    private final List<DayOfWeek> dayOfWeeks;

    public DayOfWeekCondition(final List<DayOfWeek> dayOfWeeks) {
        this.dayOfWeeks = dayOfWeeks;
    }

    @Override
    public boolean canReceive(final Order order) {
        for (final DayOfWeek dayOfWeek : dayOfWeeks) {
            if (order.hasOrderedAt(dayOfWeek)) {
                return true;
            }
        }
        return false;
    }
}
