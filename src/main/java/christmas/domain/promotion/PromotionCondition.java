package christmas.domain.promotion;

import christmas.domain.Order;
import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface PromotionCondition {

    boolean canReceive(final Order order);

    default PromotionCondition and(PromotionCondition other) {
        Objects.requireNonNull(other);
        return (t) -> canReceive(t) && other.canReceive(t);
    }

    default PromotionCondition or(PromotionCondition other) {
        Objects.requireNonNull(other);
        return (t) -> canReceive(t) || other.canReceive(t);
    }
}
