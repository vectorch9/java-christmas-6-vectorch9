package christmas.domain.promotion;

import christmas.domain.Menu;
import christmas.domain.Order;
import christmas.domain.benefit.Benefit;
import christmas.domain.benefit.GiveawayBenefit;

public class GiveawayPromotion extends Promotion{

    private final Menu target;

    private final int count;

    public GiveawayPromotion(final PromotionCondition promotionCondition, final String name, final Menu target,
            final int count) {
        super(promotionCondition, name);
        this.target = target;
        this.count = count;
    }

    @Override
    protected Benefit calculate(final Order order, final String name) {
        return new GiveawayBenefit(target, count, name);
    }
}
