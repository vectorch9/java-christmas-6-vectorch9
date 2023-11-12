package christmas.support;

import christmas.domain.Menu;
import christmas.domain.benefit.Benefit;
import christmas.domain.benefit.GiveawayBenefit;

public enum GiveawayBenefitFixtures {

    샴페인_증정_혜택(Menu.CHAMPAGNE, 1, "샴페인_증정_혜택"),
    콜라_증정_혜택(Menu.COLA, 1, "콜라_증정_혜택");


    public final Menu menu;

    public final int count;

    public final String name;

    GiveawayBenefitFixtures(final Menu menu, final int count, final String name) {
        this.menu = menu;
        this.count = count;
        this.name = name;
    }

    public Benefit 생성() {
        return new GiveawayBenefit(menu, count, name);
    }
}
