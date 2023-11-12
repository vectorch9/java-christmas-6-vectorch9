package christmas.support;

import christmas.domain.Order;
import christmas.domain.promotion.PromotionCondition;

public class AlwaysCondition implements PromotionCondition {

    private final boolean state;

    public AlwaysCondition(final boolean state) {
        this.state = state;
    }

    @Override
    public boolean canReceive(final Order order) {
        return state;
    }
}
