package christmas.support;

import christmas.domain.Money;
import christmas.domain.promotion.FixedDiscountPromotion;
import christmas.domain.promotion.Promotion;

public enum FixDiscountPromotionFixtures {

    항상_적용되는_1000원_할인(true, "항상_적용되는_1000원_할인", 1000),
    항상_적용되지_않는_1000원_할인(false, "항상_적용되지_않는_1000원_할인", 1000);

    public final boolean state;

    public final String name;

    public final int amount;

    FixDiscountPromotionFixtures(final boolean state, final String name, final int amount) {
        this.state = state;
        this.name = name;
        this.amount = amount;
    }

    public Promotion 생성() {
        return new FixedDiscountPromotion(new AlwaysCondition(state), name, 할인_금액());
    }

    public Money 할인_금액() {
        return new Money(amount);
    }
}
