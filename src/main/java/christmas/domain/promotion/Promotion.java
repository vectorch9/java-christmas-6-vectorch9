package christmas.domain.promotion;

import static christmas.error.ExceptionCode.ILLEGAL_PROMOTION_CONDITION;

import christmas.domain.Order;
import christmas.domain.benefit.Benefit;
import christmas.error.IllegalDomainException;
import java.util.Optional;

public abstract class Promotion {

    private final PromotionCondition promotionCondition;

    private final String name;

    public Promotion(final PromotionCondition promotionCondition, final String name) {
        validateCondition(promotionCondition);
        this.promotionCondition = promotionCondition;
        this.name = name;
    }

    private void validateCondition(final PromotionCondition promotionCondition) {
        if (promotionCondition == null) {
            throw new IllegalDomainException(ILLEGAL_PROMOTION_CONDITION);
        }
    }

    public Optional<Benefit> take(final Order order) {
        if (promotionCondition.canReceive(order)) {
            return Optional.of(calculate(order, name));
        }
        return Optional.empty();
    }

    protected abstract Benefit calculate(final Order order, final String name);
}
