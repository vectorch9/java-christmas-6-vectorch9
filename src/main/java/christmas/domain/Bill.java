package christmas.domain;

import christmas.domain.benefit.Benefit;
import christmas.domain.benefit.GiveawayBenefit;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Bill {

    private final Money total;

    private final Money promoted;

    private final Money promotedTotal;

    private final List<Benefit> benefits;

    private final Map<Menu, Integer> giveawayMenus;

    public Bill(final Money total, final List<Benefit> benefits) {
        this.total = total;
        this.benefits = benefits;
        this.giveawayMenus = new EnumMap<>(Menu.class);

        this.promoted = benefits.stream().map(Benefit::getPromotedMoney).reduce(Money.ZERO, Money::add);
        final Money discount = benefits.stream().filter(benefit -> !(benefit instanceof GiveawayBenefit))
                .map(Benefit::getPromotedMoney).reduce(Money.ZERO, Money::add);
        this.promotedTotal = total.subtract(discount);

        addGiveawayMenus(benefits);
    }

    private void addGiveawayMenus(final List<Benefit> benefits) {
        for (final Benefit benefit: benefits) {
            if (benefit instanceof GiveawayBenefit giveawayBenefit) {
                giveawayMenus.put(giveawayBenefit.getTarget(), giveawayBenefit.getCount());
            }
        }
    }

    public Money getTotal() {
        return total;
    }

    public Money getPromoted() {
        return promoted;
    }

    public Money getPromotedTotal() {
        return promotedTotal;
    }

    public List<Benefit> getBenefits() {
        return Collections.unmodifiableList(benefits);
    }

    public Map<Menu, Integer> getGiveawayMenus() {
        return Collections.unmodifiableMap(giveawayMenus);
    }
}
