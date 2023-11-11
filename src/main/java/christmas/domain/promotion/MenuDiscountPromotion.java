package christmas.domain.promotion;

import christmas.domain.MenuCategory;
import christmas.domain.Money;
import christmas.domain.Order;
import christmas.domain.benefit.Benefit;
import christmas.domain.benefit.DiscountBenefit;

public class MenuDiscountPromotion extends Promotion{

    private final MenuCategory category;

    private final Money money;

    public MenuDiscountPromotion(final PromotionCondition promotionCondition, final String name,
            final MenuCategory category, final Money money) {
        super(promotionCondition, name);
        this.category = category;
        this.money = money;
    }

    @Override
    protected Benefit calculate(final Order order, final String name) {
        final int count = order.countByCategory(category);
        return new DiscountBenefit(money.multiply(count), name);
    }
}
