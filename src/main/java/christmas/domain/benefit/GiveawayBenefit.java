package christmas.domain.benefit;

import christmas.domain.Menu;

public class GiveawayBenefit extends Benefit {

    private final Menu target;

    private final int count;

    public GiveawayBenefit(final Menu target, final int count, final String name) {
        super(target.getMoney(count), name);
        this.target = target;
        this.count = count;
    }

    public Menu getTarget() {
        return target;
    }

    public int getCount() {
        return count;
    }
}
