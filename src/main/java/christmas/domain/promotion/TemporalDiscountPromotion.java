package christmas.domain.promotion;

import christmas.domain.Money;
import christmas.domain.Order;
import christmas.domain.benefit.Benefit;
import christmas.domain.benefit.DiscountBenefit;
import java.time.LocalDate;

public class TemporalDiscountPromotion extends Promotion {

    private final Money base;

    private final Money increase;

    private final LocalDate startedAt;

    public TemporalDiscountPromotion(final PromotionCondition promotionCondition, final String name,
            final Money base, final Money increase, final LocalDate startedAt) {
        super(promotionCondition, name);
        this.base = base;
        this.increase = increase;
        this.startedAt = startedAt;
    }

    @Override
    protected Benefit calculate(final Order order, final String name) {
        final int days = order.getDaysAfterOrderedAt(startedAt);
        return new DiscountBenefit(base.add(increase.multiply(days)), name);
    }
}
