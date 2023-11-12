package christmas.support;

import christmas.domain.Money;
import christmas.domain.benefit.Benefit;
import christmas.domain.benefit.DiscountBenefit;

public enum DiscountBenefitFixtures {

    천원_할인_혜택(1000, "1000원_할인_혜택"),
    이천원_할인_혜택(2000, "2000원_할인_혜택");


    public final int amount;

    public final String name;

    DiscountBenefitFixtures(final int amount, final String name) {
        this.amount = amount;
        this.name = name;
    }

    public Benefit 생성() {
        return new DiscountBenefit(할인_금액(), name);
    }

    public Money 할인_금액() {
        return new Money(amount);
    }
}
